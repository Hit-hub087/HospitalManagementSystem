package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = System.getenv("HMS_DB_URL");
    private static final String USER = System.getenv("HMS_DB_USER");
    private static final String PASSWORD = System.getenv("HMS_DB_PASSWORD");

    private DBConnection() {}

    public static String validateConfiguration() {
        if (isBlank(URL) || isBlank(USER) || isBlank(PASSWORD)) {
            return "Missing DB configuration. Set HMS_DB_URL, HMS_DB_USER, and HMS_DB_PASSWORD environment variables.";
        }
        return null;
    }

    public static Connection getConnection() throws SQLException {
        String configError = validateConfiguration();
        if (configError != null) {
            throw new SQLException(configError);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Add mysql-connector-j.jar to classpath.", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String testConnection() {
        String configError = validateConfiguration();
        if (configError != null) {
            return configError;
        }

        try (Connection ignored = getConnection()) {
            return null;
        } catch (SQLException e) {
            return "Unable to connect to database: " + e.getMessage();
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
