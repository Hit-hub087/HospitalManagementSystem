package main;

import constants.AppConstants;
import constants.Messages;
import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import model.Appointment;
import model.Doctor;
import model.Patient;
import service.AppointmentService;
import service.DoctorService;
import service.PatientService;
import service.ReportService;
import util.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HospitalMenu {

    private static final Scanner SC = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(AppConstants.DATE_PATTERN);

    private static final PatientService patientService = new PatientService(new PatientDAO());
    private static final DoctorService doctorService = new DoctorService(new DoctorDAO());
    private static final AppointmentService appointmentService = new AppointmentService(new AppointmentDAO());
    private static final ReportService reportService = new ReportService(new AppointmentDAO(), new PatientDAO());

    public static void showMainMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║   HOSPITAL MANAGEMENT SYSTEM         ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Patient Management               ║");
            System.out.println("║  2. Doctor Management                ║");
            System.out.println("║  3. Appointment Management           ║");
            System.out.println("║  4. Search                           ║");
            System.out.println("║  5. Reports                          ║");
            System.out.println("║  6. Exit                             ║");
            System.out.println("╚══════════════════════════════════════╝");

            switch (readInt("Enter your choice: ")) {
                case 1 -> showPatientMenu();
                case 2 -> showDoctorMenu();
                case 3 -> showAppointmentMenu();
                case 4 -> showSearchMenu();
                case 5 -> showReportsMenu();
                case 6 -> {
                    System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                    SC.close();
                    return;
                }
                default -> System.out.println(Messages.INVALID_MENU_CHOICE);
            }
        }
    }

    private static void showPatientMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║      PATIENT MANAGEMENT      ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Add New Patient          ║");
            System.out.println("║  2. View All Patients        ║");
            System.out.println("║  3. View Patients (A-Z)      ║");
            System.out.println("║  4. Search Patient by Name   ║");
            System.out.println("║  5. Find Patient by ID       ║");
            System.out.println("║  6. Update Patient           ║");
            System.out.println("║  7. Delete Patient           ║");
            System.out.println("║  8. Back to Main Menu        ║");
            System.out.println("╚══════════════════════════════╝");

            switch (readInt("Enter your choice: ")) {
                case 1 -> addPatient();
                case 2 -> printPagedList(patientService.getAllPatients(), "All Patients");
                case 3 -> printPagedList(patientService.getAllPatientsSorted(), "Patients (A-Z)");
                case 4 -> searchPatient();
                case 5 -> findPatientById();
                case 6 -> updatePatient();
                case 7 -> deletePatient();
                case 8 -> {
                    return;
                }
                default -> System.out.println(Messages.INVALID_MENU_CHOICE);
            }
        }
    }

    private static void showDoctorMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║      DOCTOR MANAGEMENT       ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. View All Doctors         ║");
            System.out.println("║  2. View Doctors (A-Z)       ║");
            System.out.println("║  3. Search Doctor by Name    ║");
            System.out.println("║  4. Back to Main Menu        ║");
            System.out.println("╚══════════════════════════════╝");

            switch (readInt("Enter your choice: ")) {
                case 1 -> printPagedList(doctorService.getAllDoctors(), "All Doctors");
                case 2 -> printPagedList(doctorService.getAllDoctorsSorted(), "Doctors (A-Z)");
                case 3 -> searchDoctor();
                case 4 -> {
                    return;
                }
                default -> System.out.println(Messages.INVALID_MENU_CHOICE);
            }
        }
    }

    private static void showAppointmentMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║      APPOINTMENT MANAGEMENT          ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Book Appointment                 ║");
            System.out.println("║  2. View All Appointments            ║");
            System.out.println("║  3. View Appointments by Patient     ║");
            System.out.println("║  4. View Appointments by Doctor      ║");
            System.out.println("║  5. View Appointments by Date        ║");
            System.out.println("║  6. View Appointments by Status      ║");
            System.out.println("║  7. View Doctor Schedule (by date)   ║");
            System.out.println("║  8. Reschedule Appointment           ║");
            System.out.println("║  9. Update Appointment Status        ║");
            System.out.println("║ 10. Cancel Appointment               ║");
            System.out.println("║ 11. Back to Main Menu                ║");
            System.out.println("╚══════════════════════════════════════╝");

            switch (readInt("Enter your choice: ")) {
                case 1 -> bookAppointment();
                case 2 -> printPagedList(appointmentService.getAllAppointments(), "All Appointments");
                case 3 -> viewAppointmentsByPatient();
                case 4 -> viewAppointmentsByDoctor();
                case 5 -> viewAppointmentsByDate();
                case 6 -> viewAppointmentsByStatus();
                case 7 -> viewDoctorSchedule();
                case 8 -> rescheduleAppointment();
                case 9 -> updateAppointmentStatus();
                case 10 -> cancelAppointment();
                case 11 -> {
                    return;
                }
                default -> System.out.println(Messages.INVALID_MENU_CHOICE);
            }
        }
    }

    private static void showSearchMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║            SEARCH            ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Search Patient by Name   ║");
            System.out.println("║  2. Search Doctor by Name    ║");
            System.out.println("║  3. Find Patient by ID       ║");
            System.out.println("║  4. Back to Main Menu        ║");
            System.out.println("╚══════════════════════════════╝");

            switch (readInt("Enter your choice: ")) {
                case 1 -> searchPatient();
                case 2 -> searchDoctor();
                case 3 -> findPatientById();
                case 4 -> {
                    return;
                }
                default -> System.out.println(Messages.INVALID_MENU_CHOICE);
            }
        }
    }

    private static void showReportsMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║           REPORTS            ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Dashboard Summary        ║");
            System.out.println("║  2. Daily Appointment Report ║");
            System.out.println("║  3. Weekly Appointment Report║");
            System.out.println("║  4. Most Visited Doctors     ║");
            System.out.println("║  5. Patient History          ║");
            System.out.println("║  6. Back to Main Menu        ║");
            System.out.println("╚══════════════════════════════╝");

            switch (readInt("Enter your choice: ")) {
                case 1 -> printDashboard();
                case 2 -> printDailyReport();
                case 3 -> printWeeklyReport();
                case 4 -> printMostVisitedDoctors();
                case 5 -> printPatientHistory();
                case 6 -> {
                    return;
                }
                default -> System.out.println(Messages.INVALID_MENU_CHOICE);
            }
        }
    }

    private static void addPatient() {
        System.out.println("\n--- Add New Patient ---");
        String name = readValidName("Enter name: ");
        int age = readValidAge("Enter age: ");
        String gender = readValidGender("Enter gender (Male/Female/Other): ");
        String phone = readValidPhone("Enter phone (10 digits): ");
        String disease = readValidDisease("Enter disease/symptoms: ");

        boolean result = patientService.addPatient(new Patient(0, name, age, gender, phone, disease));
        System.out.println(result ? "Patient added successfully." : "Failed to add patient.");
    }

    private static void updatePatient() {
        System.out.println("\n--- Update Patient ---");
        int id = readValidId("Enter patient ID to update: ");
        Patient existing = patientService.getById(id);
        if (existing == null) {
            System.out.println("No patient found with ID: " + id);
            return;
        }

        System.out.println("Current: " + existing);
        String name = readValidName("Enter new name: ");
        int age = readValidAge("Enter new age: ");
        String gender = readValidGender("Enter new gender (Male/Female/Other): ");
        String phone = readValidPhone("Enter new phone (10 digits): ");
        String disease = readValidDisease("Enter new disease/symptoms: ");

        boolean updated = patientService.updatePatient(new Patient(id, name, age, gender, phone, disease));
        System.out.println(updated ? "Patient updated successfully." : "Failed to update patient.");
    }

    private static void searchPatient() {
        String name = readLine("\nEnter patient name to search: ").trim();
        printPagedList(patientService.searchByName(name), "Patient Search Results");
    }

    private static void findPatientById() {
        int id = readValidId("\nEnter patient ID: ");
        Patient patient = patientService.getById(id);
        if (patient == null) {
            System.out.println("No patient found with ID: " + id);
        } else {
            printDivider();
            System.out.println(patient);
            printDivider();
        }
    }

    private static void deletePatient() {
        int id = readValidId("\nEnter patient ID to delete: ");
        Patient patient = patientService.getById(id);
        if (patient == null) {
            System.out.println("No patient found with ID: " + id);
            return;
        }

        String confirm = readLine("Are you sure you want to delete " + patient.getName() + "? (yes/no): ");
        if ("yes".equalsIgnoreCase(confirm.trim())) {
            boolean deleted = patientService.deleteById(id);
            System.out.println(deleted ? "Patient deleted." : "Delete failed.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private static void searchDoctor() {
        String name = readLine("\nEnter doctor name to search: ").trim();
        printPagedList(doctorService.searchByName(name), "Doctor Search Results");
    }

    private static void bookAppointment() {
        System.out.println("\n--- Book Appointment ---");

        printPagedList(patientService.getAllPatientsSorted(), "Available Patients");
        int patientId = readValidId("Enter Patient ID: ");
        if (patientService.getById(patientId) == null) {
            System.out.println("Patient not found.");
            return;
        }

        printPagedList(doctorService.getAllDoctorsSorted(), "Available Doctors");
        int doctorId = readValidId("Enter Doctor ID: ");
        if (doctorService.getById(doctorId) == null) {
            System.out.println("Doctor not found.");
            return;
        }

        String date = readValidDate("Enter date (DD-MM-YYYY): ", true);

        printDoctorScheduleForBooking(doctorId, date);
        System.out.println("Available slots: " + String.join("  ", AppConstants.VALID_TIME_SLOTS));
        String slot = readValidTimeSlot("Enter time slot: ");

        Appointment appointment = new Appointment(0, patientId, doctorId, date, slot, AppConstants.STATUS_SCHEDULED);
        boolean booked = appointmentService.bookAppointment(appointment);
        System.out.println(booked ? "Appointment booked successfully." : "Booking failed. Slot may already be taken.");
    }

    private static void viewAppointmentsByPatient() {
        int id = readValidId("\nEnter Patient ID: ");
        printPagedList(appointmentService.getByPatient(id), "Appointments for Patient ID " + id);
    }

    private static void viewAppointmentsByDoctor() {
        int id = readValidId("\nEnter Doctor ID: ");
        printPagedList(appointmentService.getByDoctor(id), "Appointments for Doctor ID " + id);
    }

    private static void viewAppointmentsByDate() {
        String date = readValidDate("\nEnter date (DD-MM-YYYY): ", false);
        printPagedList(appointmentService.getByDate(date), "Appointments on " + date);
    }

    private static void viewAppointmentsByStatus() {
        String status = readLine("\nEnter status (Scheduled/Completed/Cancelled): ").trim();
        printPagedList(appointmentService.getByStatus(status), "Appointments with status " + status);
    }

    private static void viewDoctorSchedule() {
        int doctorId = readValidId("\nEnter Doctor ID: ");
        String date = readValidDate("Enter date (DD-MM-YYYY): ", false);
        printPagedList(appointmentService.getDoctorSchedule(doctorId, date), "Doctor " + doctorId + " Schedule on " + date);
    }

    private static void rescheduleAppointment() {
        int id = readValidId("\nEnter Appointment ID to reschedule: ");
        if (!appointmentService.appointmentExists(id)) {
            System.out.println("No appointment found with ID: " + id);
            return;
        }

        String newDate = readValidDate("Enter new date (DD-MM-YYYY): ", true);
        String newSlot = readValidTimeSlot("Enter new time slot: ");

        boolean result = appointmentService.rescheduleAppointment(id, newDate, newSlot);
        System.out.println(result ? "Appointment rescheduled successfully." : "Reschedule failed. Slot may already be taken.");
    }

    private static void updateAppointmentStatus() {
        int id = readValidId("\nEnter Appointment ID: ");
        if (!appointmentService.appointmentExists(id)) {
            System.out.println("No appointment found with ID: " + id);
            return;
        }

        String status = readLine("Enter new status (Scheduled/Completed/Cancelled): ").trim();
        boolean updated = appointmentService.updateAppointmentStatus(id, status);
        System.out.println(updated ? "Appointment status updated." : "Status update failed.");
    }

    private static void cancelAppointment() {
        int id = readValidId("\nEnter Appointment ID to cancel: ");
        boolean result = appointmentService.cancelAppointment(id);
        System.out.println(result ? "Appointment cancelled successfully." : "Cancellation failed.");
    }

    private static void printDashboard() {
        System.out.println("\n--- Dashboard ---");
        Map<String, Integer> dashboard = reportService.getDashboardCounts();
        dashboard.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    private static void printDailyReport() {
        String date = readValidDate("\nEnter date (DD-MM-YYYY): ", false);
        System.out.println("\n--- Daily Report: " + date + " ---");
        reportService.getDailyReport(date).forEach((k, v) -> System.out.println(k + ": " + v));
    }

    private static void printWeeklyReport() {
        String start = readValidDate("\nEnter start date (DD-MM-YYYY): ", false);
        LocalDate startDate = LocalDate.parse(start, DATE_FORMATTER);
        System.out.println("\n--- Weekly Report ---");
        reportService.getWeeklyReport(startDate).forEach((k, v) -> System.out.println(k + ": " + v));
    }

    private static void printMostVisitedDoctors() {
        System.out.println("\n--- Most Visited Doctors ---");
        List<String> rows = reportService.getMostVisitedDoctors();
        if (rows.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        rows.forEach(System.out::println);
    }

    private static void printPatientHistory() {
        int patientId = readValidId("\nEnter Patient ID: ");
        printPagedList(reportService.getPatientHistory(patientId), "Patient History");
    }

    private static int readInt(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                System.out.println(Messages.INVALID_NUMBER);
            }
        }
    }

    private static int readValidId(String prompt) {
        while (true) {
            int id = readInt(prompt);
            if (InputValidator.isValidPositiveId(id)) {
                return id;
            }
            System.out.println(Messages.INVALID_ID);
        }
    }

    private static String readValidName(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            if (InputValidator.isValidName(value)) {
                return value;
            }
            System.out.println(Messages.INVALID_NAME);
        }
    }

    private static int readValidAge(String prompt) {
        while (true) {
            int age = readInt(prompt);
            if (InputValidator.isValidAge(age)) {
                return age;
            }
            System.out.println(Messages.INVALID_AGE);
        }
    }

    private static String readValidGender(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            if (InputValidator.isValidGender(value)) {
                return value;
            }
            System.out.println(Messages.INVALID_GENDER);
        }
    }

    private static String readValidPhone(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            if (InputValidator.isValidPhone(value)) {
                return value;
            }
            System.out.println(Messages.INVALID_PHONE);
        }
    }

    private static String readValidDisease(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            if (InputValidator.isValidDisease(value)) {
                return value;
            }
            System.out.println(Messages.INVALID_DISEASE);
        }
    }

    private static String readValidDate(String prompt, boolean mustBeFutureOrToday) {
        while (true) {
            String value = readLine(prompt).trim();
            LocalDate date = InputValidator.parseDate(value);
            if (date != null && (!mustBeFutureOrToday || InputValidator.isFutureOrToday(date))) {
                return value;
            }
            System.out.println(Messages.INVALID_DATE);
        }
    }

    private static String readValidTimeSlot(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            if (InputValidator.isValidTimeSlot(value)) {
                return value;
            }
            System.out.println(Messages.INVALID_TIME_SLOT);
        }
    }

    private static void printPagedList(List<?> list, String title) {
        System.out.println("\n--- " + title + " ---");
        if (list == null || list.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        int pageSize = AppConstants.PAGE_SIZE;
        int totalPages = (int) Math.ceil((double) list.size() / pageSize);

        for (int page = 0; page < totalPages; page++) {
            int start = page * pageSize;
            int end = Math.min(start + pageSize, list.size());

            printDivider();
            for (int i = start; i < end; i++) {
                System.out.println(list.get(i));
            }
            printDivider();
            System.out.println("Page " + (page + 1) + " of " + totalPages + " | Total records: " + list.size());

            if (page < totalPages - 1) {
                String input = readLine("Press Enter for next page or type 'back' to stop: ");
                if ("back".equalsIgnoreCase(input.trim())) {
                    return;
                }
            }
        }
    }

    private static void printDoctorScheduleForBooking(int doctorId, String date) {
        List<Appointment> schedule = appointmentService.getDoctorSchedule(doctorId, date);
        if (schedule.isEmpty()) {
            System.out.println("No booked slots for this doctor on " + date + ".");
            return;
        }

        System.out.println("Booked slots for doctor " + doctorId + " on " + date + ":");
        schedule.stream()
                .filter(a -> AppConstants.STATUS_SCHEDULED.equalsIgnoreCase(a.getStatus()))
                .forEach(a -> System.out.println("- " + a.getTimeSlot() + " (Appointment ID: " + a.getAppointmentId() + ")"));
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    private static void printDivider() {
        System.out.println("----------------------------------------");
    }
}
