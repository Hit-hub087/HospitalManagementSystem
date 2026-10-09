package main;

import util.DBConnection;

public class Main {
    public static void main(String[] args) {
        String startupError = DBConnection.testConnection();
        if (startupError != null) {
            System.out.println("Startup check failed: " + startupError);
            System.out.println("Tip: export HMS_DB_URL, HMS_DB_USER, HMS_DB_PASSWORD and restart.");
            return;
        }

        System.out.println("Database connection verified. Launching Hospital Management System...");
        HospitalMenu.showMainMenu();
    }
}
