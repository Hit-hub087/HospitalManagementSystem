package util;

import constants.AppConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(AppConstants.DATE_PATTERN);

    private InputValidator() {}

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        String trimmed = name.trim();
        return trimmed.length() >= 2 && trimmed.matches("[A-Za-z ]+");
    }

    public static boolean isValidAge(int age) {
        return age > 0 && age <= 120;
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public static boolean isValidGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male")
                || gender.equalsIgnoreCase("Female")
                || gender.equalsIgnoreCase("Other"));
    }

    public static boolean isValidDisease(String disease) {
        return disease != null && disease.trim().length() >= 3;
    }

    public static boolean isValidPositiveId(int id) {
        return id > 0;
    }

    public static boolean isValidDate(String date) {
        return parseDate(date) != null;
    }

    public static LocalDate parseDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }
        try {
            LocalDate parsed = LocalDate.parse(date.trim(), DATE_FORMATTER);
            String reformatted = parsed.format(DATE_FORMATTER);
            return reformatted.equals(date.trim()) ? parsed : null;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean isFutureOrToday(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.now());
    }

    public static boolean isValidTimeSlot(String slot) {
        return slot != null && AppConstants.VALID_TIME_SLOTS.contains(slot.trim());
    }
}
