package com.hospital.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection manager for SQL Server
 */
public class DatabaseConnection {
    private static final String SERVER = "localhost";
    private static final String DATABASE = "QuanLyBenhVien";
    private static final String USERNAME = "hospital_admin";
    private static final String PASSWORD = "YourStrongPassword123!"; // Change this to your SQL Server password
    private static final int PORT = 1433;

    private static Connection connection;

    /**
     * Get database connection
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:sqlserver://" + SERVER + ":" + PORT + 
                        ";databaseName=" + DATABASE + 
                        ";encrypt=true;trustServerCertificate=true";
            
            try {
                connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
                System.out.println("Connected to database successfully!");
            } catch (SQLException e) {
                System.err.println("Failed to connect to database: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
