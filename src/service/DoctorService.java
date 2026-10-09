package service;

import dao.DoctorDAO;
import model.Doctor;
import util.InputValidator;

import java.util.List;

public class DoctorService {
    private final DoctorDAO doctorDAO;

    public DoctorService(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    public List<Doctor> getAllDoctors() {
        return doctorDAO.getAllDoctors();
    }

    public List<Doctor> getAllDoctorsSorted() {
        return doctorDAO.getAllDoctorsSortedByName();
    }

    public List<Doctor> searchByName(String name) {
        return doctorDAO.searchDoctorByName(name == null ? "" : name.trim());
    }

    public Doctor getById(int id) {
        return InputValidator.isValidPositiveId(id) ? doctorDAO.getDoctorById(id) : null;
    }
}
