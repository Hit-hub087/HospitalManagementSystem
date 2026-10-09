import util.InputValidator;

public class InputValidatorTest {

    public static void main(String[] args) {
        assertTrue(InputValidator.isValidName("John Doe"), "valid name");
        assertFalse(InputValidator.isValidName("J"), "name too short");
        assertFalse(InputValidator.isValidName(""), "empty name");

        assertTrue(InputValidator.isValidAge(25), "valid age");
        assertFalse(InputValidator.isValidAge(0), "invalid age");

        assertTrue(InputValidator.isValidPhone("9876543210"), "valid phone");
        assertFalse(InputValidator.isValidPhone("12345"), "invalid phone");

        assertTrue(InputValidator.isValidDate("09-10-2026"), "valid date");
        assertFalse(InputValidator.isValidDate("32-13-2026"), "invalid calendar date");

        assertTrue(InputValidator.isValidTimeSlot("10:00"), "valid slot");
        assertFalse(InputValidator.isValidTimeSlot("13:00"), "invalid slot");

        assertTrue(InputValidator.isValidDisease("Flu fever"), "valid disease");
        assertFalse(InputValidator.isValidDisease("ok"), "disease too short");

        System.out.println("InputValidatorTest passed.");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Expected true: " + message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Expected false: " + message);
        }
    }
}
