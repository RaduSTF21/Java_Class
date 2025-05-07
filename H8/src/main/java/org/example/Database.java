package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

    private Database() {}

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() {
        try {
            connection = ConnectionPool.getConnection();
            System.out.println("Database connection established successfully");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    public static void rollback() {
        try {
            if (connection != null) {
                connection.rollback();
                System.out.println("Transaction rolled back");
            }
        } catch (SQLException e) {
            System.err.println("Error during rollback: " + e.getMessage());
        }
    }
}