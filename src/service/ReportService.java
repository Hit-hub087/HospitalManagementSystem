package service;

import constants.AppConstants;
import dao.AppointmentDAO;
import dao.PatientDAO;
import model.Appointment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(AppConstants.DATE_PATTERN);

    public ReportService(AppointmentDAO appointmentDAO, PatientDAO patientDAO) {
        this.appointmentDAO = appointmentDAO;
        this.patientDAO = patientDAO;
    }

    public Map<String, Integer> getDashboardCounts() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        String today = LocalDate.now().format(FORMATTER);
        counts.put("Today's Appointments", appointmentDAO.countAppointmentsByDate(today));
        counts.put("Total Patients", patientDAO.getAllPatients().size());
        counts.put("Cancelled Appointments", appointmentDAO.countAppointmentsByStatus(AppConstants.STATUS_CANCELLED));
        return counts;
    }

    public Map<String, Integer> getDailyReport(String date) {
        Map<String, Integer> report = new LinkedHashMap<>();
        List<Appointment> daily = appointmentDAO.getAppointmentsByDate(date);
        report.put("Total", daily.size());
        report.put("Scheduled", (int) daily.stream().filter(a -> AppConstants.STATUS_SCHEDULED.equalsIgnoreCase(a.getStatus())).count());
        report.put("Completed", (int) daily.stream().filter(a -> AppConstants.STATUS_COMPLETED.equalsIgnoreCase(a.getStatus())).count());
        report.put("Cancelled", (int) daily.stream().filter(a -> AppConstants.STATUS_CANCELLED.equalsIgnoreCase(a.getStatus())).count());
        return report;
    }

    public Map<String, Integer> getWeeklyReport(LocalDate startDate) {
        Map<String, Integer> weekly = new LinkedHashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate day = startDate.plusDays(i);
            String formatted = day.format(FORMATTER);
            weekly.put(formatted, appointmentDAO.countAppointmentsByDate(formatted));
        }
        return weekly;
    }

    public List<String> getMostVisitedDoctors() {
        return appointmentDAO.getMostVisitedDoctorSummary();
    }

    public List<Appointment> getPatientHistory(int patientId) {
        List<Appointment> history = new ArrayList<>(appointmentDAO.getAppointmentsByPatient(patientId));
        history.sort((a, b) -> a.getAppointmentDate().compareTo(b.getAppointmentDate()));
        return history;
    }
}
