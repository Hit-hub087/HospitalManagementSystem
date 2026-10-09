CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

CREATE TABLE IF NOT EXISTS patient (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age BETWEEN 1 AND 120),
    gender VARCHAR(20) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    disease VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS doctor (
    doctor_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS appointment (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date VARCHAR(10) NOT NULL,
    time_slot VARCHAR(5) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Scheduled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE,
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE RESTRICT,
    CONSTRAINT chk_appointment_status CHECK (status IN ('Scheduled', 'Completed', 'Cancelled')),
    CONSTRAINT uq_appointment_doctor_slot_status UNIQUE (doctor_id, appointment_date, time_slot, status)
);

CREATE INDEX idx_patient_name ON patient(name);
CREATE INDEX idx_doctor_name ON doctor(name);
CREATE INDEX idx_appointment_patient ON appointment(patient_id);
CREATE INDEX idx_appointment_date_slot ON appointment(appointment_date, time_slot);
CREATE INDEX idx_appointment_status ON appointment(status);
