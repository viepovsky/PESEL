package io.github.viepovsky.polishutils.pesel;

/**
 * The {@code PeselNumbers} class holds the digits of a PESEL number as fields. Each field
 * corresponds to a specific part of the PESEL (date of birth, serial number, gender, and
 * control number).
 * <p>
 * This class is package-private and is not intended for use outside of the org.viepovsky.pesel package.
 * <p>
 * The fields of this class are immutable and can only be set during object creation via
 * the constructor.
 *
 * @author Oskar Rajzner
 */
class PeselNumbers {
    /**
     * Year, month, and day digits of the PESEL number in form of YYMMDD (digits 1-6).
     */
    private final int birthDate;
    /**
     * Unique serial number of the PESEL in form of ZZZ (digits 7-9).
     */
    private final int serial;
    /**
     * Gender digit of the PESEL in form of X (digit 10).
     */
    private final int gender;
    /**
     * Control sum digit of the PESEL in form of Q (digit 11).
     */
    private final int control;

    /**
     * Constructs a new {@code PeselNumbers} object from the given PESEL string.
     * The given PESEL is validated to ensure that it is not null, is of the correct length,
     * and only contains digits. If the validation fails, an {@link InvalidPeselException} is
     * thrown.
     *
     * @param pesel the PESEL number
     * @if the given PESEL is invalid
     */
    PeselNumbers(String pesel) {
        PeselValidator.assertIsNotNull(pesel);
        PeselValidator.assertIsLengthValid(pesel);
        PeselValidator.assertIsOnlyDigits(pesel);

        birthDate = Integer.parseInt(pesel.substring(0, 6));
        serial = Integer.parseInt(pesel.substring(6, 9));
        gender = Character.getNumericValue(pesel.charAt(9));
        control = Character.getNumericValue(pesel.charAt(10));
    }

    /**
     * Returns the year, month, and day digits of the PESEL number as an integer in the
     * form of YYMMDD.
     *
     * @return an integer representing the year, month, and day digits of the PESEL number
     */
    public int getBirthDateNumbers() {
        return birthDate;
    }

    /**
     * Returns the unique serial number of the PESEL as an integer in the form of ZZZ.
     *
     * @return an integer representing the unique serial number of the PESEL
     */
    public int getSerialNumbers() {
        return serial;
    }

    /**
     * Returns the single digit representing the gender of the person associated with the
     * PESEL as an integer.
     *
     * @return an integer representing the gender digit of the PESEL
     */
    public int getGenderNumber() {
        return gender;
    }

    /**
     * Returns the single digit representing the control sum of the PESEL as an integer.
     *
     * @return an integer representing the control sum of the PESEL
     */
    public int getControlNumber() {
        return control;
    }
}
