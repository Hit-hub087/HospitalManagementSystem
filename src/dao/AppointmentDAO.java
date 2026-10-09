package dao;

import constants.AppConstants;
import model.Appointment;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public boolean bookAppointment(Appointment appointment) {
        String checkSql = "SELECT appointment_id FROM appointment WHERE doctor_id = ? AND appointment_date = ? " +
                "AND time_slot = ? AND status = ? FOR UPDATE";
        String insertSql = "INSERT INTO appointment (patient_id, doctor_id, appointment_date, time_slot, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                    checkPs.setInt(1, appointment.getDoctorId());
                    checkPs.setString(2, appointment.getAppointmentDate());
                    checkPs.setString(3, appointment.getTimeSlot());
                    checkPs.setString(4, AppConstants.STATUS_SCHEDULED);
                    try (ResultSet rs = checkPs.executeQuery()) {
                        if (rs.next()) {
                            conn.rollback();
                            return false;
                        }
                    }
                }

                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setInt(1, appointment.getPatientId());
                    insertPs.setInt(2, appointment.getDoctorId());
                    insertPs.setString(3, appointment.getAppointmentDate());
                    insertPs.setString(4, appointment.getTimeSlot());
                    insertPs.setString(5, AppConstants.STATUS_SCHEDULED);
                    boolean inserted = insertPs.executeUpdate() > 0;
                    if (inserted) {
                        conn.commit();
                    } else {
                        conn.rollback();
                    }
                    return inserted;
                }
            } catch (SQLException inner) {
                conn.rollback();
                throw inner;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSlotBooked(int doctorId, String date, String timeSlot) {
        String sql = "SELECT appointment_id FROM appointment WHERE doctor_id = ? AND appointment_date = ? " +
                "AND time_slot = ? AND status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setString(2, date);
            ps.setString(3, timeSlot);
            ps.setString(4, AppConstants.STATUS_SCHEDULED);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, time_slot, status FROM appointment " +
                "ORDER BY STR_TO_DATE(appointment_date, '%d-%m-%Y') ASC, time_slot ASC";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(buildAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Appointment> getAppointmentsByPatient(int patientId) {
        return getAppointmentsByField("patient_id", patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return getAppointmentsByField("doctor_id", doctorId);
    }

    public List<Appointment> getAppointmentsByDate(String date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, time_slot, status FROM appointment " +
                "WHERE appointment_date = ? ORDER BY time_slot ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(buildAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Appointment> getAppointmentsByStatus(String status) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, time_slot, status FROM appointment " +
                "WHERE status = ? ORDER BY STR_TO_DATE(appointment_date, '%d-%m-%Y') ASC, time_slot ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(buildAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Appointment> getDoctorScheduleByDate(int doctorId, String date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, time_slot, status FROM appointment " +
                "WHERE doctor_id = ? AND appointment_date = ? ORDER BY time_slot ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setString(2, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(buildAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean cancelAppointment(int appointmentId) {
        return updateAppointmentStatus(appointmentId, AppConstants.STATUS_CANCELLED);
    }

    public boolean updateAppointmentStatus(int appointmentId, String status) {
        String sql = "UPDATE appointment SET status = ? WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rescheduleAppointment(int appointmentId, String newDate, String newSlot) {
        String fetchSql = "SELECT doctor_id FROM appointment WHERE appointment_id = ?";
        String lockSql = "SELECT appointment_id FROM appointment WHERE doctor_id = ? AND appointment_date = ? AND time_slot = ? " +
                "AND status = ? AND appointment_id <> ? FOR UPDATE";
        String updateSql = "UPDATE appointment SET appointment_date = ?, time_slot = ?, status = ? WHERE appointment_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Integer doctorId;
                try (PreparedStatement fetchPs = conn.prepareStatement(fetchSql)) {
                    fetchPs.setInt(1, appointmentId);
                    try (ResultSet rs = fetchPs.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }
                        doctorId = rs.getInt("doctor_id");
                    }
                }

                try (PreparedStatement lockPs = conn.prepareStatement(lockSql)) {
                    lockPs.setInt(1, doctorId);
                    lockPs.setString(2, newDate);
                    lockPs.setString(3, newSlot);
                    lockPs.setString(4, AppConstants.STATUS_SCHEDULED);
                    lockPs.setInt(5, appointmentId);
                    try (ResultSet rs = lockPs.executeQuery()) {
                        if (rs.next()) {
                            conn.rollback();
                            return false;
                        }
                    }
                }

                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setString(1, newDate);
                    updatePs.setString(2, newSlot);
                    updatePs.setString(3, AppConstants.STATUS_SCHEDULED);
                    updatePs.setInt(4, appointmentId);
                    boolean updated = updatePs.executeUpdate() > 0;
                    if (updated) {
                        conn.commit();
                    } else {
                        conn.rollback();
                    }
                    return updated;
                }
            } catch (SQLException inner) {
                conn.rollback();
                throw inner;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean appointmentExists(int appointmentId) {
        String sql = "SELECT appointment_id FROM appointment WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM appointment WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countAppointmentsByDate(String date) {
        String sql = "SELECT COUNT(*) FROM appointment WHERE appointment_date = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countAppointmentsByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM appointment WHERE status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<String> getMostVisitedDoctorSummary() {
        List<String> summary = new ArrayList<>();
        String sql = "SELECT d.doctor_id, d.name, COUNT(*) AS visits FROM appointment a " +
                "JOIN doctor d ON a.doctor_id = d.doctor_id " +
                "GROUP BY d.doctor_id, d.name ORDER BY visits DESC LIMIT 5";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                summary.add("Doctor ID: " + rs.getInt("doctor_id") + " | Name: " + rs.getString("name") + " | Visits: " + rs.getInt("visits"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }

    private List<Appointment> getAppointmentsByField(String fieldName, int id) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, time_slot, status FROM appointment " +
                "WHERE " + fieldName + " = ? ORDER BY STR_TO_DATE(appointment_date, '%d-%m-%Y') ASC, time_slot ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(buildAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Appointment buildAppointment(ResultSet rs) throws SQLException {
        return new Appointment(
                rs.getInt("appointment_id"),
                rs.getInt("patient_id"),
                rs.getInt("doctor_id"),
                rs.getString("appointment_date"),
                rs.getString("time_slot"),
                rs.getString("status")
        );
    }
}
