package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ClientThread implements Runnable {
    private Socket clientSocket;
    private GameServer server;
    private PrintWriter out;
    private BufferedReader in;
    private String clientId;
    private boolean running = true;

    public ClientThread(Socket socket, GameServer server) {
        this.clientSocket = socket;
        this.server = server;
        this.clientId = UUID.randomUUID().toString();
    }

    @Override
    public void run() {
        try {

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            server.registerClient(clientId, this);


            out.println("CONNECTED " + clientId);

            String inputLine;
            while (running && (inputLine = in.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("exit")) {
                    break;
                }

                String response = server.handleCommand(clientId, inputLine);
                out.println(response);

                if (response.equals("Server stopped")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error in client communication: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println("MESSAGE " + message);
        }
    }

    private void disconnect() {
        running = false;
        server.removeClient(clientId);

        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            System.out.println("Client " + clientId + " disconnected");
        } catch (IOException e) {
            System.err.println("Error closing client connection: " + e.getMessage());
        }
    }
}

