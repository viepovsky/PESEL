package org.viepovsky.pesel;

import java.time.DateTimeException;

public abstract class PeselValidator {
    private static final int[] CONTROL_WAGES = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
    private static final PeselDecoder PESEL_DECODER = new PeselDecoder();

    public static boolean isValid(String pesel) {
        try {
            assertIsValid(pesel);
            return true;
        } catch (InvalidPeselException e) {
            return false;
        }
    }

    public static void assertIsValid(String pesel) throws InvalidPeselException {
        if (pesel == null) {
            throw new InvalidPeselException("PESEL cannot be null");
        }
        if (pesel.length() != 11) {
            throw new InvalidPeselException("PESEL length is invalid");
        }
        if (!pesel.matches("[0-9]*")) {
            throw new InvalidPeselException("PESEL contains invalid characters");
        }
        if (!isControlDigitValid(pesel)) {
            throw new InvalidPeselException("PESEL control sum number is invalid");
        }
        if (!isBirthDateValid(pesel)) {
            throw new InvalidPeselException("PESEL birth date is invalid");
        }
    }

    private static boolean isControlDigitValid(String pesel) {
        int controlSum = 0, controlDigit = Character.getNumericValue(pesel.charAt(10));
        for (int i = 0; i < 10; i++) {
            int multiplyNumber = CONTROL_WAGES[i] * Character.getNumericValue(pesel.charAt(i));
            if (multiplyNumber >= 10) {
                controlSum += Character.getNumericValue(String.valueOf(multiplyNumber).charAt(1));
            } else {
                controlSum += multiplyNumber;
            }
        }
        if (controlSum >= 10) {
            return controlDigit == (10 - Character.getNumericValue(String.valueOf(controlSum).charAt(1)));
        }
        return controlDigit == (10 - controlSum);
    }

    private static boolean isBirthDateValid(String pesel) {
        try {
            PESEL_DECODER.decodeBirthDate(pesel);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
