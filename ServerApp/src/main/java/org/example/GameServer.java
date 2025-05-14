package org.example;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private ServerSocket serverSocket;
    private final int port;
    private boolean running = false;
    private ExecutorService pool;
    private Map<String, ClientThread> clients;

    public GameServer(int port) {
        this.port = port;
        this.clients = new HashMap<>();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            pool = Executors.newCachedThreadPool();
            System.out.println("Server started on port " + port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                ClientThread clientThread = new ClientThread(clientSocket, this);
                pool.execute(clientThread);
            }

        } catch (IOException e) {
            if (running) {
                System.err.println("Server error: " + e.getMessage());
            }
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
        pool.shutdown();

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server stopped");
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }
    }

    public synchronized String handleCommand(String clientId, String command) {
        System.out.println("Received command from " + clientId + ": " + command);

        if (command.equalsIgnoreCase("stop")) {
            stop();
            return "Server stopped";
        }

        String[] parts = command.split("\\s+");
        String response;




                response = "Server received the request: " + command;

        return response;
    }



    public synchronized void registerClient(String clientId, ClientThread clientThread) {
        clients.put(clientId, clientThread);
    }

    public synchronized void removeClient(String clientId) {
        clients.remove(clientId);
    }




}