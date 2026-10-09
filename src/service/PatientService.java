package service;

import dao.PatientDAO;
import model.Patient;
import util.InputValidator;

import java.util.List;

public class PatientService {
    private final PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public boolean addPatient(Patient patient) {
        return isValidPatient(patient) && patientDAO.addPatient(patient);
    }

    public boolean updatePatient(Patient patient) {
        return InputValidator.isValidPositiveId(patient.getPatientId())
                && isValidPatient(patient)
                && patientDAO.updatePatient(patient);
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    public List<Patient> getAllPatientsSorted() {
        return patientDAO.getAllPatientsSortedByName();
    }

    public List<Patient> searchByName(String name) {
        return patientDAO.searchPatientByName(name == null ? "" : name.trim());
    }

    public Patient getById(int id) {
        return InputValidator.isValidPositiveId(id) ? patientDAO.getPatientById(id) : null;
    }

    public boolean deleteById(int id) {
        return InputValidator.isValidPositiveId(id) && patientDAO.deletePatient(id);
    }

    private boolean isValidPatient(Patient patient) {
        return patient != null
                && InputValidator.isValidName(patient.getName())
                && InputValidator.isValidAge(patient.getAge())
                && InputValidator.isValidGender(patient.getGender())
                && InputValidator.isValidPhone(patient.getPhone())
                && InputValidator.isValidDisease(patient.getDisease());
    }
}
