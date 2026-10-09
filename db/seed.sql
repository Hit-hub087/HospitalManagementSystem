USE hospital_db;

INSERT INTO doctor (name, specialization, phone) VALUES
('Dr. Arjun Mehta', 'Cardiology', '9876543210'),
('Dr. Neha Sharma', 'Dermatology', '9876543211'),
('Dr. Ravi Iyer', 'Orthopedics', '9876543212')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO patient (name, age, gender, phone, disease) VALUES
('Rohan Kumar', 29, 'Male', '9123456789', 'Fever and Cough'),
('Anjali Singh', 42, 'Female', '9234567890', 'Joint Pain'),
('Vikram Das', 35, 'Male', '9345678901', 'Skin Rash');

INSERT INTO appointment (patient_id, doctor_id, appointment_date, time_slot, status)
SELECT p.patient_id, d.doctor_id, DATE_FORMAT(CURDATE(), '%d-%m-%Y'), '10:00', 'Scheduled'
FROM patient p
JOIN doctor d ON d.name = 'Dr. Arjun Mehta'
WHERE p.name = 'Rohan Kumar'
LIMIT 1;
