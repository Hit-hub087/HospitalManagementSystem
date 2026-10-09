# Hospital Management System

A console-based Hospital Management System built using Core Java,
JDBC, and MySQL. Solves the real-world problem of managing hospital
appointments, patients, and doctors efficiently.

## Features
- Add, view, search and delete patients
- View and search doctors
- Book appointments with double-booking prevention
- Cancel appointments
- Input validation on all user inputs (name, age, phone, gender, date)
- Clean layered architecture (Model → DAO → UI)

## Tech Stack
- Java (Core Java, OOP)
- JDBC (Java Database Connectivity)
- MySQL (Relational Database)
- IntelliJ IDEA (IDE)

## Project Structure

src/
├── model/     → Patient.java, Doctor.java, Appointment.java
├── dao/       → PatientDAO.java, DoctorDAO.java, AppointmentDAO.java
├── util/      → DBConnection.java, InputValidator.java
└── main/      → Main.java, HospitalMenu.java

## Database Setup
1. Open MySQL Workbench
2. Create database hospital_db
3. Create tables: patient, doctor, appointment
4. Update DBConnection.java with your MySQL password

## How to Run
1. Add mysql-connector-j.jar to project dependencies
2. Set up the database
3. Run Main.java

## OOP Concepts Used
- Encapsulation: All model fields are private with getters/setters
- Abstraction: DAO layer hides SQL complexity from the UI
- Object Creation: Every patient/doctor/appointment is a Java object

## Design Pattern Used
DAO Pattern — separates business logic from database logic.
Each entity has its own DAO class.

## Developer
Name:    Hitesh Gupta
Year:    6th sem B.Tech
