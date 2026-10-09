package util;

public class InputValidator {

    // ✅ Check name is not empty
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // ✅ Check age is between 1 and 120
    public static boolean isValidAge(int age) {
        return age > 0 && age <= 120;
    }

    // ✅ Check phone is exactly 10 digits
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // ✅ Check date format is DD-MM-YYYY
    public static boolean isValidDate(String date) {
        return date != null && date.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    // ✅ Check time slot is valid
    public static boolean isValidTimeSlot(String slot) {
        String[] validSlots = {
                "09:00", "10:00", "11:00", "12:00",
                "14:00", "15:00", "16:00", "17:00"
        };
        for (String s : validSlots) {
            if (s.equals(slot)) return true;
        }
        return false;
    }
    // ✅ Check gender is valid
    public static boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("Male") ||
                gender.equalsIgnoreCase("Female") ||
                gender.equalsIgnoreCase("Other");
    }
}