package common;

/**
 * Shared validation helpers used by Main.java / SystemManager to keep
 * input-checking consistent across every menu operation.
 *
 * Owned by: shared (created as part of the project skeleton)
 * Supports assignment requirement 13/14 (input validation, handling
 * invalid input).
 */
public class InputValidator {

    private InputValidator() {
        // Utility class — no instances
    }

    public static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * A simple Student ID format check: non-empty, no surrounding
     * whitespace-only content. Kept intentionally permissive since the
     * assignment doesn't mandate a specific ID format — tighten this
     * if your team agrees on one (e.g. must start with a letter).
     */
    public static boolean isValidId(String id) {
        return isNonEmpty(id);
    }

    /**
     * Marks must be a valid number between 0 and 100 inclusive.
     */
    public static boolean isValidMarks(double marks) {
        return marks >= 0.0 && marks <= 100.0;
    }

    /**
     * Attempts to parse a marks value from user input text.
     *
     * @return the parsed value, or -1 if the input was not a valid
     *         number (caller should treat -1 as "invalid input").
     */
    public static double parseMarks(String input) {
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }
}
