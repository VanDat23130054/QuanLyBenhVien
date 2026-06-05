package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String DEFAULT_SERVER = "localhost";
    private static final String DEFAULT_DATABASE = "QuanLyBenhVien";
    private static final String DEFAULT_USER = "sa";
    private static final String DEFAULT_PASSWORD = "YourStrongPassword123!";
    private static final String DEFAULT_PORT = "1433";

    private static volatile Properties config;

    private static void loadConfig() throws IOException {
        if (config != null) return;
        synchronized (DatabaseConnection.class) {
            if (config != null) return;
            Properties p = new Properties();
            try (InputStream in = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (in != null) {
                    p.load(in);
                }
            }
            config = p;
        }
    }

    private static String getConfig(String key, String defaultValue) {
        try {
            loadConfig();
            String v = config.getProperty(key);
            return (v == null || v.isEmpty()) ? defaultValue : v;
        } catch (IOException e) {
            // If loading properties fails, fall back to defaults
            return defaultValue;
        }
    }

    private static String buildConnectionUrl() {
        String server = getConfig("db.server", DEFAULT_SERVER);
        String port = getConfig("db.port", DEFAULT_PORT);
        String database = getConfig("db.name", DEFAULT_DATABASE);
        // trustServerCertificate=true is included to avoid SSL issues on local dev; production should use proper certs
        return "jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + database + ";trustServerCertificate=true";
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = buildConnectionUrl();
            String user = getConfig("db.username", DEFAULT_USER);
            String password = getConfig("db.password", DEFAULT_PASSWORD);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection!");
                e.printStackTrace();
            }
        }
    }
}