package com.httt.quanlybenhvien;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Simple database configuration helper that reads connection properties from
 * classpath:/application.properties and provides a Connection via DriverManager.
 * This is intentionally lightweight so you can adapt to a connection pool later.
 */
public class DatabaseConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                props.load(in);
            } else {
                System.err.println("application.properties not found on classpath - using defaults");
            }
            String driver = props.getProperty("db.driverClassName");
            if (driver != null && !driver.isEmpty()) {
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException e) {
                    // Driver optional if using JDBC 4+; still log for clarity
                    System.err.println("JDBC driver class not found: " + driver + " -> " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load database configuration: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = props.getProperty("db.url", "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBenhVien;encrypt=false");
        String user = props.getProperty("db.user", "sa");
        String password = props.getProperty("db.password", "YourStrongPassword123!");
        return DriverManager.getConnection(url, user, password);
    }
}
