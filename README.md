# Hospital Management System

A console-based Hospital Management System built with Core Java, JDBC, and MySQL.

## What’s Improved
- Secure DB configuration through environment variables (no hardcoded credentials)
- Startup configuration and connection checks with friendly errors
- Stronger validation for name, ID, disease, date, and time slot
- Service layer added between menu and DAO
- DAO refactored with try-with-resources and transactional appointment booking
- New flows: update patient, update appointment status, reschedule, schedule/day views
- Filtering and sorting options with paginated console output
- Reporting dashboard and daily/weekly insights
- SQL schema + seed files included
- Unit/integration-style Java tests + smoke script

## Project Structure

```
src/
├── constants/  -> AppConstants.java, Messages.java
├── model/      -> Patient.java, Doctor.java, Appointment.java
├── dao/        -> PatientDAO.java, DoctorDAO.java, AppointmentDAO.java
├── service/    -> PatientService.java, DoctorService.java, AppointmentService.java, ReportService.java
├── util/       -> DBConnection.java, InputValidator.java
└── main/       -> Main.java, HospitalMenu.java

db/
├── schema.sql
└── seed.sql

tests/
├── InputValidatorTest.java
├── AppointmentServiceTest.java
└── DaoIntegrationTest.java

scripts/
└── smoke_test.sh
```

## Prerequisites
- Java 17+ (or Java 11+ with minor syntax adjustments if needed)
- MySQL 8+
- MySQL JDBC driver (`mysql-connector-j.jar`) in your classpath

## Database Setup
1. Run schema:
   ```sql
   SOURCE /absolute/path/to/db/schema.sql;
   ```
2. (Optional) Seed sample data:
   ```sql
   SOURCE /absolute/path/to/db/seed.sql;
   ```

## Environment Configuration
Set environment variables before running:

```bash
export HMS_DB_URL="jdbc:mysql://localhost:3306/hospital_db"
export HMS_DB_USER="root"
export HMS_DB_PASSWORD="your_password"
```

## Compile and Run

```bash
cd /home/runner/work/HospitalManagementSystem/HospitalManagementSystem
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out main.Main
```

## Run Tests

```bash
cd /home/runner/work/HospitalManagementSystem/HospitalManagementSystem
./scripts/smoke_test.sh
```

This runs:
- `InputValidatorTest` (unit-style checks)
- `AppointmentServiceTest` (business-rule checks)
- `DaoIntegrationTest` (DAO integration smoke check)

## Sample Console Flow
- Open main menu
- Add or update patient
- View doctors and doctor schedule for a selected date
- Book appointment (future/today date only)
- Reschedule or update appointment status
- Use reports menu for dashboard/daily/weekly summaries

## Troubleshooting

### 1) MySQL Driver not found
- Ensure `mysql-connector-j.jar` is included in classpath/IDE dependencies.

### 2) Missing DB configuration
- Set `HMS_DB_URL`, `HMS_DB_USER`, `HMS_DB_PASSWORD` in your shell.

### 3) DB authentication error
- Verify username/password and MySQL host/port in `HMS_DB_URL`.

### 4) Table missing errors
- Run `db/schema.sql` against your `hospital_db` database.

### 5) Booking fails due to slot conflict
- Slot uniqueness is enforced for doctor + date + time + scheduled status.

