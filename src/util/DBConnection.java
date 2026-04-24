package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // These 3 values connect us to MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASSWORD = "hd4458$abc12"; // change if yours is different

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Step 1: Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Create the connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database connected successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found! Did you add the JAR?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed! Check your username/password.");
            e.printStackTrace();
        }
        return conn;
    }
}