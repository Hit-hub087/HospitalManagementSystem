package model;

public class Patient {

    // Fields — these match exactly with columns in your patient table
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String disease;

    // Constructor — used to create a Patient object with all values
    public Patient(int patientId, String name, int age,
                   String gender, String phone, String disease) {
        this.patientId = patientId;
        this.name      = name;
        this.age       = age;
        this.gender    = gender;
        this.phone     = phone;
        this.disease   = disease;
    }

    // Getters — used to READ the values
    public int getPatientId()  { return patientId; }
    public String getName()    { return name; }
    public int getAge()        { return age; }
    public String getGender()  { return gender; }
    public String getPhone()   { return phone; }
    public String getDisease() { return disease; }

    // Setters — used to UPDATE the values
    public void setPatientId(int patientId)   { this.patientId = patientId; }
    public void setName(String name)          { this.name = name; }
    public void setAge(int age)               { this.age = age; }
    public void setGender(String gender)      { this.gender = gender; }
    public void setPhone(String phone)        { this.phone = phone; }
    public void setDisease(String disease)    { this.disease = disease; }

    // toString — used to print patient details easily
    @Override
    public String toString() {
        return "ID: " + patientId +
                " | Name: " + name +
                " | Age: " + age +
                " | Gender: " + gender +
                " | Phone: " + phone +
                " | Disease: " + disease;
    }
}