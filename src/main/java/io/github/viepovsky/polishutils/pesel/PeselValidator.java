package io.github.viepovsky.polishutils.pesel;

import java.time.DateTimeException;

/**
 * The {@code PeselValidator} class validates Polish Personal Identification Number (PESEL).
 * <p>
 * Example usage of the {@code PeselValidator} class:
 * <blockquote><pre>
 *     PeselValidator.assertIsValid("92082683499"); //may throw {@link InvalidPeselException}
 *     or
 *     PeselValidator.isValid("92082683499"); //returns true or false
 * </pre></blockquote>
 * Passing a {@code null} argument will cause a {@link NullPointerException} to be thrown.
 * Passing an invalid PESEL to assertIsValid method will cause
 * an {@link InvalidPeselException} to be thrown.
 *
 * @author Oskar Rajzner
 */
public abstract class PeselValidator {

    /**
     * Digits weight for checking the validity of the PESEL
     */
    private static final int[] CONTROL_WEIGHTS = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1};

    /**
     * An instance of the {@code PeselDecoder} class.
     */
    private static final PeselDecoder PESEL_DECODER = new PeselDecoder();

    /**
     * Checks if given PESEL is valid.
     *
     * @param pesel the PESEL number
     * @return true if the given PESEL is valid, false otherwise
     */
    public static boolean isPeselValid(String pesel) {
        try {
            assertIsPeselValid(pesel);
            return true;
        } catch (InvalidPeselException e) {
            return false;
        }
    }

    /**
     * Checks if given PESEL is valid.
     *
     * @param pesel the PESEL number
     * @throws InvalidPeselException if the given PESEL is invalid
     */
    public static void assertIsPeselValid(String pesel) {
        assertIsNotNull(pesel);
        assertIsLengthValid(pesel);
        assertIsOnlyDigits(pesel);
        assertIsControlDigitValid(pesel);
        assertIsBirthDateValid(pesel);
    }

    /**
     * Checks if given PESEL is null.
     *
     * @param pesel the PESEL number
     * @throws NullPointerException if given PESEL is null
     */
    protected static void assertIsNotNull(String pesel) {
        if (pesel == null) {
            throw new NullPointerException("PESEL cannot be null");
        }
    }

    /**
     * Checks if given PESEL has length of 11.
     *
     * @param pesel the PESEL number
     * @throws InvalidPeselException if given PESEL length is other than 11
     */
    protected static void assertIsLengthValid(String pesel) {
        if (pesel.length() != 11) {
            throw new InvalidPeselException("PESEL length is invalid, should be 11 numbers");
        }
    }

    /**
     * Checks if given PESEL has only digits.
     *
     * @param pesel the PESEL number
     * @throws InvalidPeselException if given PESEL has other characters than digits
     */
    protected static void assertIsOnlyDigits(String pesel) {
        if (!pesel.matches("[0-9]*")) {
            throw new InvalidPeselException("PESEL contains invalid characters, should contain only digits 0-9");
        }
    }

    /**
     * Checks if control digit is valid.
     *
     * @param pesel the PESEL number
     * @throws InvalidPeselException if given PESEL has invalid control number
     */
    protected static void assertIsControlDigitValid(String pesel) {
        if (!isControlDigitValid(pesel)) {
            throw new InvalidPeselException("PESEL control sum number is invalid");
        }
    }

    /**
     * Checks if control digit is valid.
     *
     * @param pesel the PESEL number
     * @return true if the given PESEL control digit is valid, false otherwise
     */
    protected static boolean isControlDigitValid(String pesel) {
        int sum = 0;
        for (int i = 0; i <= 10; i++) {
            int multipliedNumber = CONTROL_WEIGHTS[i] * Character.getNumericValue(pesel.charAt(i));
            sum += multipliedNumber;
        }
        String controlSum = String.valueOf(sum);
        int lastDigit = Integer.parseInt(controlSum.substring(controlSum.length() - 1));
        return lastDigit == 0;
    }

    /**
     * Checks if the date of birth is valid.
     *
     * @param pesel the PESEL number
     * @throws InvalidPeselException if the given PESEL date of birth is invalid
     */
    protected static void assertIsBirthDateValid(String pesel) {
        if (!isBirthDateValid(pesel)) {
            throw new InvalidPeselException("PESEL birth date is invalid");
        }
    }

    /**
     * Checks if the date of birth is valid.
     *
     * @param pesel the PESEL number
     * @return true if the given PESEL date of birth is valid, false otherwise
     */
    protected static boolean isBirthDateValid(String pesel) {
        try {
            PESEL_DECODER.decodeBirthDate(pesel);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}