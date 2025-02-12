package tcmapp.util;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost/testjira";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private static final String ERROR_LOG_FILE = "error_log.txt";

    static {
        try {
            Class.forName("org.postgresql.Driver", true, DatabaseUtil.class.getClassLoader());
            System.out.println("✅ PostgreSQL Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            logError("PostgreSQL JDBC Driver not found! Make sure it's added to the project.", e);
            throw new RuntimeException("Database driver not found!", e);
        }
    }

    // Connect to Database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logError("Failed to connect to the database!", e);
            return null;
        }
    }

    // Log Error to a File
    private static void logError(String message, Exception e) {
        try (FileWriter writer = new FileWriter(ERROR_LOG_FILE, true)) {
            writer.write(message + "\n");
            writer.write("Exception: " + e.getMessage() + "\n\n");
            System.err.println(message);
        } catch (IOException ioException) {
            System.err.println("Failed to write error log: " + ioException.getMessage());
        }
    }

    // Main Method to Test Connection
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Database connected successfully!");
        } else {
            System.out.println("Database connection failed. Check error_log.txt.");
        }
    }
}
