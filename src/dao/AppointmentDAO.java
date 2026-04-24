package dao;

import model.Appointment;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // ✅ CHECK if a slot is already booked (prevents double booking)
    public boolean isSlotBooked(int doctorId, String date, String timeSlot) {
        String sql = "SELECT * FROM appointment WHERE doctor_id = ? " +
                "AND appointment_date = ? AND time_slot = ? " +
                "AND status = 'Scheduled'";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, doctorId);
            ps.setString(2, date);
            ps.setString(3, timeSlot);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true means slot is already taken
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ BOOK a new appointment (with double booking check)
    public boolean bookAppointment(Appointment a) {

        // First check if slot is already taken
        if (isSlotBooked(a.getDoctorId(), a.getAppointmentDate(), a.getTimeSlot())) {
            System.out.println("Sorry! This slot is already booked.");
            return false;
        }

        String sql = "INSERT INTO appointment (patient_id, doctor_id, " +
                "appointment_date, time_slot, status) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setString(3, a.getAppointmentDate());
            ps.setString(4, a.getTimeSlot());
            ps.setString(5, "Scheduled");
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ VIEW all appointments
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointment";
        try {
            Connection conn = DBConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("appointment_date"),
                        rs.getString("time_slot"),
                        rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ VIEW appointments by patient ID
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointment WHERE patient_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("appointment_date"),
                        rs.getString("time_slot"),
                        rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ CANCEL an appointment
    public boolean cancelAppointment(int appointmentId) {
        // First check if appointment exists
        if (!appointmentExists(appointmentId)) {
            System.out.println("No appointment found with ID: " + appointmentId);
            return false;
        }
        String sql = "UPDATE appointment SET status = 'Cancelled' " +
                "WHERE appointment_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ CHECK if appointment exists
    public boolean appointmentExists(int appointmentId) {
        String sql = "SELECT * FROM appointment WHERE appointment_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ DELETE an appointment permanently
    public boolean deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM appointment WHERE appointment_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}