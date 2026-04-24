package model;

public class Doctor {

    private int doctorId;
    private String name;
    private String specialization;
    private String phone;

    // Constructor
    public Doctor(int doctorId, String name,
                  String specialization, String phone) {
        this.doctorId       = doctorId;
        this.name           = name;
        this.specialization = specialization;
        this.phone          = phone;
    }

    // Getters
    public int getDoctorId()           { return doctorId; }
    public String getName()            { return name; }
    public String getSpecialization()  { return specialization; }
    public String getPhone()           { return phone; }

    // Setters
    public void setDoctorId(int doctorId)              { this.doctorId = doctorId; }
    public void setName(String name)                   { this.name = name; }
    public void setSpecialization(String spec)         { this.specialization = spec; }
    public void setPhone(String phone)                 { this.phone = phone; }

    // toString
    @Override
    public String toString() {
        return "ID: " + doctorId +
                " | Name: " + name +
                " | Specialization: " + specialization +
                " | Phone: " + phone;
    }
}