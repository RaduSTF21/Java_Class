package org.example;

public class Main {
    public static void main(String[] args) {
        int port = 8888;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default: " + port);
            }
        }

        GameServer server = new GameServer(port);
        server.start();
    }
}