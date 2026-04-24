package model;

public class Appointment {

    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String appointmentDate;
    private String timeSlot;
    private String status;

    // Constructor
    public Appointment(int appointmentId, int patientId, int doctorId,
                       String appointmentDate, String timeSlot, String status) {
        this.appointmentId   = appointmentId;
        this.patientId       = patientId;
        this.doctorId        = doctorId;
        this.appointmentDate = appointmentDate;
        this.timeSlot        = timeSlot;
        this.status          = status;
    }

    // Getters
    public int getAppointmentId()      { return appointmentId; }
    public int getPatientId()          { return patientId; }
    public int getDoctorId()           { return doctorId; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getTimeSlot()        { return timeSlot; }
    public String getStatus()          { return status; }

    // Setters
    public void setAppointmentId(int appointmentId)        { this.appointmentId = appointmentId; }
    public void setPatientId(int patientId)                { this.patientId = patientId; }
    public void setDoctorId(int doctorId)                  { this.doctorId = doctorId; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setTimeSlot(String timeSlot)               { this.timeSlot = timeSlot; }
    public void setStatus(String status)                   { this.status = status; }

    // toString
    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId +
                " | Patient ID: " + patientId +
                " | Doctor ID: " + doctorId +
                " | Date: " + appointmentDate +
                " | Slot: " + timeSlot +
                " | Status: " + status;
    }
}