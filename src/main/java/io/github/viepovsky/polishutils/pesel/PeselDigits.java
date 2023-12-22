package io.github.viepovsky.polishutils.pesel;

/**
 * The {@code PeselDigits} class holds the digits of a PESEL number as fields. Each field
 * corresponds to a specific part of the PESEL (date of birth, serial number, gender, and
 * control digit).
 * <p>
 * This class is package-private and is not intended for use outside the org.viepovsky.pesel package.
 * <p>
 *
 * @author Oskar Rajzner
 */
abstract class PeselDigits {

    /**
     * Year, month, and day digits of the PESEL number in form of YYMMDD (digits 1-6).
     */
    private int birthDateDigits;

    /**
     * Unique serial digits of the PESEL in form of ZZZ (digits 7-9).
     */
    private int serialDigits;

    /**
     * Gender digit of the PESEL in form of X (digit 10).
     */
    private int genderDigit;

    /**
     * Control sum digit of the PESEL in form of Q (digit 11).
     */
    private int controlDigit;

    /**
     * Constructs a new {@code PeselDigits} object.
     */
    PeselDigits() {
    }

    /**
     * Returns the year, month, and day digits of the PESEL number as an integer in the
     * form of YYMMDD.
     *
     * @return an integer representing the year, month, and day digits of the PESEL number
     */
    public int getBirthDateDigits() {
        return birthDateDigits;
    }

    /**
     * Sets the year, month, and day digits of the PESEL number as an integer in the
     * form of YYMMDD.
     *
     * @param pesel the PESEL number
     */
    void setBirthDateDigits(String pesel) {
        birthDateDigits = Integer.parseInt(pesel.substring(0, 6));
    }

    /**
     * Returns the unique serial digits of the PESEL as an integer in the form of ZZZ.
     *
     * @return an integer representing the unique serial digits of the PESEL
     */
    public int getSerialDigits() {
        return serialDigits;
    }

    /**
     * Sets the unique serial digits of the PESEL as an integer in the form of ZZZ.
     *
     * @param pesel the PESEL number
     */
    void setSerialDigits(String pesel) {
        serialDigits = Integer.parseInt(pesel.substring(6, 9));
    }

    /**
     * Returns the single digit representing the gender of the person associated with the
     * PESEL as an integer.
     *
     * @return an integer representing the gender digit of the PESEL
     */
    public int getGenderDigit() {
        return genderDigit;
    }

    /**
     * Returns the single digit representing the gender of the person associated with the
     * PESEL as an integer.
     *
     * @param pesel the PESEL number
     */
    void setGenderDigit(String pesel) {
        genderDigit = Character.getNumericValue(pesel.charAt(9));
    }

    /**
     * Returns the single digit representing the control sum of the PESEL as an integer.
     *
     * @return an integer representing the control sum of the PESEL
     */
    public int getControlDigit() {
        return controlDigit;
    }

    /**
     * Sets the single digit representing the control sum of the PESEL as an integer.
     *
     * @param pesel the PESEL number
     */
    void setControlDigit(String pesel) {
        controlDigit = Character.getNumericValue(pesel.charAt(10));
    }
}
