package service;

import constants.AppConstants;
import dao.AppointmentDAO;
import model.Appointment;
import util.InputValidator;

import java.time.LocalDate;
import java.util.List;

public class AppointmentService {
    private final AppointmentDAO appointmentDAO;

    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public boolean bookAppointment(Appointment appointment) {
        if (!InputValidator.isValidPositiveId(appointment.getPatientId())
                || !InputValidator.isValidPositiveId(appointment.getDoctorId())
                || !InputValidator.isValidTimeSlot(appointment.getTimeSlot())) {
            return false;
        }

        LocalDate parsedDate = InputValidator.parseDate(appointment.getAppointmentDate());
        if (!InputValidator.isFutureOrToday(parsedDate)) {
            return false;
        }

        appointment.setStatus(AppConstants.STATUS_SCHEDULED);
        return appointmentDAO.bookAppointment(appointment);
    }

    public boolean cancelAppointment(int appointmentId) {
        return InputValidator.isValidPositiveId(appointmentId)
                && appointmentDAO.updateAppointmentStatus(appointmentId, AppConstants.STATUS_CANCELLED);
    }

    public boolean updateAppointmentStatus(int appointmentId, String status) {
        if (!InputValidator.isValidPositiveId(appointmentId)) {
            return false;
        }
        if (!AppConstants.STATUS_SCHEDULED.equalsIgnoreCase(status)
                && !AppConstants.STATUS_COMPLETED.equalsIgnoreCase(status)
                && !AppConstants.STATUS_CANCELLED.equalsIgnoreCase(status)) {
            return false;
        }
        String normalized = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        return appointmentDAO.updateAppointmentStatus(appointmentId, normalized);
    }

    public boolean rescheduleAppointment(int appointmentId, String newDate, String newSlot) {
        if (!InputValidator.isValidPositiveId(appointmentId) || !InputValidator.isValidTimeSlot(newSlot)) {
            return false;
        }

        LocalDate date = InputValidator.parseDate(newDate);
        if (!InputValidator.isFutureOrToday(date)) {
            return false;
        }

        return appointmentDAO.rescheduleAppointment(appointmentId, newDate, newSlot);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    public List<Appointment> getByPatient(int patientId) {
        return InputValidator.isValidPositiveId(patientId)
                ? appointmentDAO.getAppointmentsByPatient(patientId)
                : List.of();
    }

    public List<Appointment> getByDoctor(int doctorId) {
        return InputValidator.isValidPositiveId(doctorId)
                ? appointmentDAO.getAppointmentsByDoctor(doctorId)
                : List.of();
    }

    public List<Appointment> getByDate(String date) {
        LocalDate parsedDate = InputValidator.parseDate(date);
        return parsedDate == null ? List.of() : appointmentDAO.getAppointmentsByDate(date);
    }

    public List<Appointment> getByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return List.of();
        }
        return appointmentDAO.getAppointmentsByStatus(status.trim());
    }

    public List<Appointment> getDoctorSchedule(int doctorId, String date) {
        if (!InputValidator.isValidPositiveId(doctorId) || InputValidator.parseDate(date) == null) {
            return List.of();
        }
        return appointmentDAO.getDoctorScheduleByDate(doctorId, date);
    }

    public boolean appointmentExists(int id) {
        return InputValidator.isValidPositiveId(id) && appointmentDAO.appointmentExists(id);
    }
}
