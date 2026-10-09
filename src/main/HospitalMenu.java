package main;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import model.Appointment;
import model.Doctor;
import model.Patient;
import util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class HospitalMenu {

    // DAO objects — created once and reused throughout
    private static PatientDAO     patientDAO     = new PatientDAO();
    private static DoctorDAO      doctorDAO      = new DoctorDAO();
    private static AppointmentDAO appointmentDAO = new AppointmentDAO();
    private static Scanner        sc             = new Scanner(System.in);

    // ─────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────
    public static void showMainMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║   HOSPITAL MANAGEMENT SYSTEM         ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Patient Management               ║");
            System.out.println("║  2. Doctor Management                ║");
            System.out.println("║  3. Appointment Management           ║");
            System.out.println("║  4. Search                           ║");
            System.out.println("║  5. Exit                             ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: showPatientMenu();      break;
                case 2: showDoctorMenu();       break;
                case 3: showAppointmentMenu();  break;
                case 4: showSearchMenu();       break;
                case 5:
                    System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 1 to 5.");
            }
        }
    }

    // ─────────────────────────────────────────
    //  PATIENT MENU
    // ─────────────────────────────────────────
    public static void showPatientMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║      PATIENT MANAGEMENT      ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Add New Patient          ║");
            System.out.println("║  2. View All Patients        ║");
            System.out.println("║  3. Search Patient by Name   ║");
            System.out.println("║  4. Delete Patient           ║");
            System.out.println("║  5. Back to Main Menu        ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: addPatient();         break;
                case 2: viewAllPatients();    break;
                case 3: searchPatient();      break;
                case 4: deletePatient();      break;
                case 5: return; // goes back to main menu
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ─────────────────────────────────────────
    //  DOCTOR MENU
    // ─────────────────────────────────────────
    public static void showDoctorMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║      DOCTOR MANAGEMENT       ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. View All Doctors         ║");
            System.out.println("║  2. Search Doctor by Name    ║");
            System.out.println("║  3. Back to Main Menu        ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: viewAllDoctors();  break;
                case 2: searchDoctor();    break;
                case 3: return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ─────────────────────────────────────────
    //  APPOINTMENT MENU
    // ─────────────────────────────────────────
    public static void showAppointmentMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║    APPOINTMENT MANAGEMENT        ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Book Appointment             ║");
            System.out.println("║  2. View All Appointments        ║");
            System.out.println("║  3. View Appointments by Patient ║");
            System.out.println("║  4. Cancel Appointment           ║");
            System.out.println("║  5. Back to Main Menu            ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: bookAppointment();              break;
                case 2: viewAllAppointments();          break;
                case 3: viewAppointmentsByPatient();    break;
                case 4: cancelAppointment();            break;
                case 5: return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ─────────────────────────────────────────
    //  SEARCH MENU
    // ─────────────────────────────────────────
    public static void showSearchMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║         SEARCH               ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Search Patient by Name   ║");
            System.out.println("║  2. Search Doctor by Name    ║");
            System.out.println("║  3. Find Patient by ID       ║");
            System.out.println("║  4. Back to Main Menu        ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: searchPatient();   break;
                case 2: searchDoctor();    break;
                case 3: findPatientById(); break;
                case 4: return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ═════════════════════════════════════════
    //  PATIENT OPERATIONS
    // ═════════════════════════════════════════

    private static void addPatient() {
        System.out.println("\n--- Add New Patient ---");

        // Name with retry
        String name = "";
        while (true) {
            System.out.print("Enter name: ");
            name = sc.nextLine().trim();
            if (InputValidator.isValidName(name)) break;
            System.out.println("Invalid name! Cannot be empty.");
        }

        // Age with retry
        int age = 0;
        while (true) {
            System.out.print("Enter age: ");
            age = getIntInput();
            if (InputValidator.isValidAge(age)) break;
            System.out.println("Invalid age! Must be between 1 and 120.");
        }

        // Gender with retry
        String gender = "";
        while (true) {
            System.out.print("Enter gender (Male/Female/Other): ");
            gender = sc.nextLine().trim();
            if (InputValidator.isValidGender(gender)) break;
            System.out.println("Invalid! Please enter Male, Female, or Other.");
        }

        // Phone with retry
        String phone = "";
        while (true) {
            System.out.print("Enter phone (10 digits): ");
            phone = sc.nextLine().trim();
            if (InputValidator.isValidPhone(phone)) break;
            System.out.println("Invalid phone! Must be exactly 10 digits.");
        }

        // Disease with retry
        String disease = "";
        while (true) {
            System.out.print("Enter disease/symptoms: ");
            disease = sc.nextLine().trim();
            if (InputValidator.isValidName(disease)) break;
            System.out.println("Cannot be empty!");
        }

        Patient p = new Patient(0, name, age, gender, phone, disease);
        boolean result = patientDAO.addPatient(p);

        if (result) {
            System.out.println("Patient added successfully!");
        } else {
            System.out.println("Failed to add patient. Please try again.");
        }
    }

    private static void viewAllPatients() {
        System.out.println("\n--- All Patients ---");
        List<Patient> list = patientDAO.getAllPatients();
        if (list.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        printDivider();
        for (Patient p : list) {
            System.out.println(p);
        }
        printDivider();
        System.out.println("Total patients: " + list.size());
    }

    private static void searchPatient() {
        System.out.print("\nEnter patient name to search: ");
        String name = sc.nextLine().trim();
        List<Patient> list = patientDAO.searchPatientByName(name);
        if (list.isEmpty()) {
            System.out.println("No patients found with name: " + name);
            return;
        }
        printDivider();
        for (Patient p : list) {
            System.out.println(p);
        }
        printDivider();
    }

    private static void findPatientById() {
        System.out.print("\nEnter patient ID: ");
        int id = getIntInput();
        Patient p = patientDAO.getPatientById(id);
        if (p == null) {
            System.out.println("No patient found with ID: " + id);
        } else {
            printDivider();
            System.out.println(p);
            printDivider();
        }
    }

    private static void deletePatient() {
        System.out.print("\nEnter patient ID to delete: ");
        int id = getIntInput();

        Patient p = patientDAO.getPatientById(id);
        if (p == null) {
            System.out.println("No patient found with ID: " + id);
            return;
        }

        System.out.println("Are you sure you want to delete: " + p.getName() + "? (yes/no): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes")) {
            boolean result = patientDAO.deletePatient(id);
            System.out.println(result ? "Patient deleted." : "Delete failed.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ═════════════════════════════════════════
    //  DOCTOR OPERATIONS
    // ═════════════════════════════════════════

    private static void viewAllDoctors() {
        System.out.println("\n--- All Doctors ---");
        List<Doctor> list = doctorDAO.getAllDoctors();
        if (list.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        printDivider();
        for (Doctor d : list) {
            System.out.println(d);
        }
        printDivider();
        System.out.println("Total doctors: " + list.size());
    }

    private static void searchDoctor() {
        System.out.print("\nEnter doctor name to search: ");
        String name = sc.nextLine().trim();
        List<Doctor> list = doctorDAO.searchDoctorByName(name);
        if (list.isEmpty()) {
            System.out.println("No doctors found with name: " + name);
            return;
        }
        printDivider();
        for (Doctor d : list) {
            System.out.println(d);
        }
        printDivider();
    }

    // ═════════════════════════════════════════
    //  APPOINTMENT OPERATIONS
    // ═════════════════════════════════════════

    private static void bookAppointment() {
        System.out.println("\n--- Book Appointment ---");

        // Show all patients first
        System.out.println("Available Patients:");
        viewAllPatients();
        System.out.print("Enter Patient ID: ");
        int patientId = getIntInput();

        if (patientDAO.getPatientById(patientId) == null) {
            System.out.println("Patient not found!");
            return;
        }

        // Show all doctors
        System.out.println("\nAvailable Doctors:");
        viewAllDoctors();
        System.out.print("Enter Doctor ID: ");
        int doctorId = getIntInput();

        if (doctorDAO.getDoctorById(doctorId) == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.print("Enter date (DD-MM-YYYY): ");
        String date = sc.nextLine().trim();
        if (!InputValidator.isValidDate(date)) {
            System.out.println("Invalid date format! Use DD-MM-YYYY.");
            return;
        }

        System.out.println("Available slots: 09:00  10:00  11:00  12:00  14:00  15:00  16:00  17:00");
        System.out.print("Enter time slot: ");
        String slot = sc.nextLine().trim();
        if (!InputValidator.isValidTimeSlot(slot)) {
            System.out.println("Invalid time slot!");
            return;
        }

        Appointment a = new Appointment(0, patientId, doctorId, date, slot, "Scheduled");
        boolean result = appointmentDAO.bookAppointment(a);

        if (result) {
            System.out.println("Appointment booked successfully!");
        } else {
            System.out.println("Booking failed. Slot may already be taken.");
        }
    }

    private static void viewAllAppointments() {
        System.out.println("\n--- All Appointments ---");
        List<Appointment> list = appointmentDAO.getAllAppointments();
        if (list.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        printDivider();
        for (Appointment a : list) {
            System.out.println(a);
        }
        printDivider();
        System.out.println("Total appointments: " + list.size());
    }

    private static void viewAppointmentsByPatient() {
        System.out.print("\nEnter Patient ID: ");
        int id = getIntInput();
        List<Appointment> list = appointmentDAO.getAppointmentsByPatient(id);
        if (list.isEmpty()) {
            System.out.println("No appointments found for patient ID: " + id);
            return;
        }
        printDivider();
        for (Appointment a : list) {
            System.out.println(a);
        }
        printDivider();
    }

    private static void cancelAppointment() {
        System.out.print("\nEnter Appointment ID to cancel: ");
        int id = getIntInput();
        boolean result = appointmentDAO.cancelAppointment(id);
        System.out.println(result ? "Appointment cancelled successfully!" : "Cancellation failed.");
    }

    // ═════════════════════════════════════════
    //  HELPER METHODS
    // ═════════════════════════════════════════

    // Safely reads an integer — handles wrong input without crashing
    private static int getIntInput() {
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static void printDivider() {
        System.out.println("----------------------------------------");
    }
}