import dao.AppointmentDAO;
import model.Appointment;
import service.AppointmentService;

import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceTest {

    public static void main(String[] args) {
        FakeAppointmentDAO fakeDao = new FakeAppointmentDAO();
        AppointmentService service = new AppointmentService(fakeDao);

        Appointment invalidPastDate = new Appointment(0, 1, 2, "01-01-2020", "10:00", "Scheduled");
        assertFalse(service.bookAppointment(invalidPastDate), "should reject past date");

        Appointment invalidSlot = new Appointment(0, 1, 2, "09-10-2099", "13:00", "Scheduled");
        assertFalse(service.bookAppointment(invalidSlot), "should reject invalid slot");

        Appointment valid = new Appointment(0, 1, 2, "09-10-2099", "10:00", "Scheduled");
        assertTrue(service.bookAppointment(valid), "should accept valid booking");
        assertTrue(fakeDao.bookCalled, "DAO booking should be called");

        assertFalse(service.updateAppointmentStatus(0, "Completed"), "invalid id should fail");
        assertFalse(service.updateAppointmentStatus(1, "Unknown"), "invalid status should fail");
        assertTrue(service.updateAppointmentStatus(1, "completed"), "valid status should pass");

        assertFalse(service.rescheduleAppointment(1, "32-01-2026", "10:00"), "invalid date should fail");
        assertFalse(service.rescheduleAppointment(1, "09-10-2026", "13:00"), "invalid slot should fail");

        System.out.println("AppointmentServiceTest passed.");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Expected true: " + message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Expected false: " + message);
        }
    }

    private static class FakeAppointmentDAO extends AppointmentDAO {
        boolean bookCalled;

        @Override
        public boolean bookAppointment(Appointment appointment) {
            bookCalled = true;
            return true;
        }

        @Override
        public boolean updateAppointmentStatus(int appointmentId, String status) {
            return appointmentId > 0;
        }

        @Override
        public boolean rescheduleAppointment(int appointmentId, String newDate, String newSlot) {
            return appointmentId > 0;
        }

        @Override
        public boolean appointmentExists(int appointmentId) {
            return appointmentId > 0;
        }

        @Override
        public List<Appointment> getAppointmentsByPatient(int patientId) {
            return new ArrayList<>();
        }

        @Override
        public List<Appointment> getAppointmentsByDoctor(int doctorId) {
            return new ArrayList<>();
        }

        @Override
        public List<Appointment> getAppointmentsByDate(String date) {
            return new ArrayList<>();
        }

        @Override
        public List<Appointment> getAppointmentsByStatus(String status) {
            return new ArrayList<>();
        }

        @Override
        public List<Appointment> getDoctorScheduleByDate(int doctorId, String date) {
            return new ArrayList<>();
        }
    }
}
