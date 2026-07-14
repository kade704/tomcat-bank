package com.example.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String DB_HOST = System.getenv().getOrDefault("DB_HOST", "oracle");
    private static final String DB_PORT = System.getenv().getOrDefault("DB_PORT", "1521");
    private static final String DB_NAME = System.getenv().getOrDefault("DB_NAME", "FREEPDB1");
    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "dbuser");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "password");

    private static final String JDBC_URL = String.format(
            "jdbc:oracle:thin:@//%s:%s/%s",
            DB_HOST, DB_PORT, DB_NAME);

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver not found: " + e.getMessage());
            return null;
        }

        try {
            return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
        return null;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}
