package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;
    private ExecutorService executorService;
    private boolean running = false;
    private String clientId = null;

    public GameClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            scanner = new Scanner(System.in);
            executorService = Executors.newSingleThreadExecutor();
            System.out.println("Connected to server " + host + ":" + port);
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    public void start() {
        if (socket == null || socket.isClosed()) {
            System.err.println("Not connected to server");
            return;
        }

        running = true;


        executorService.execute(() -> {
            try {
                String response;
                while (running && (response = in.readLine()) != null) {
                    if (response.startsWith("CONNECTED ")) {
                        clientId = response.substring("CONNECTED ".length());
                        System.out.println("Connected with ID: " + clientId);
                        printHelp();
                    } else if (response.startsWith("MESSAGE ")) {
                        System.out.println("[SERVER]: " + response.substring("MESSAGE ".length()));
                    } else {
                        System.out.println("[SERVER]: " + response);
                    }
                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error reading from server: " + e.getMessage());
                }
            } finally {
                stop();
            }
        });


        try {
            String command;
            while (running && (command = scanner.nextLine()) != null) {
                if (command.equalsIgnoreCase("exit")) {
                    break;
                } else if (command.equalsIgnoreCase("help")) {
                    printHelp();
                    continue;
                }

                out.println(command);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            stop();
        }
    }

    private void printHelp() {
        System.out.println("\n--- Hex Game Client Help ---");
        System.out.println("Available commands:");
        System.out.println("  create <game_name>        - Create a new game");
        System.out.println("  join <game_name>          - Join an existing game");
        System.out.println("  move <row> <col>          - Make a move at the specified coordinates");
        System.out.println("  list                      - List all available games");
        System.out.println("  help                      - Display this help message");
        System.out.println("  exit                      - Disconnect from the server and exit");
        System.out.println("  stop                      - Stop the server (admin only)");
        System.out.println("-----------------------------\n");
    }

    public void stop() {
        running = false;
        executorService.shutdown();

        try {
            if (scanner != null) scanner.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8888;


        if (args.length >= 1) {
            host = args[0];
        }
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default: " + port);
            }
        }

        GameClient client = new GameClient(host, port);
        client.start();
    }
}

