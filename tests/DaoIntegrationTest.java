import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import util.DBConnection;

public class DaoIntegrationTest {

    public static void main(String[] args) {
        String error = DBConnection.testConnection();
        if (error != null) {
            System.out.println("DaoIntegrationTest skipped: " + error);
            return;
        }

        PatientDAO patientDAO = new PatientDAO();
        DoctorDAO doctorDAO = new DoctorDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        if (patientDAO.getAllPatients() == null) {
            throw new AssertionError("PatientDAO returned null list");
        }
        if (doctorDAO.getAllDoctors() == null) {
            throw new AssertionError("DoctorDAO returned null list");
        }
        if (appointmentDAO.getAllAppointments() == null) {
            throw new AssertionError("AppointmentDAO returned null list");
        }

        System.out.println("DaoIntegrationTest passed.");
    }
}
