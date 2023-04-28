package org.viepovsky.pesel;

class PeselNumbers {
    private final int birthDate;
    private final int serial;
    private final int gender;
    private final int control;

    PeselNumbers(String pesel) throws InvalidPeselException {
        PeselValidator.assertIsValid(pesel);
        birthDate = Integer.parseInt(pesel.substring(0, 6));
        serial = Integer.parseInt(pesel.substring(6, 9));
        gender = Character.getNumericValue(pesel.charAt(9));
        control = Character.getNumericValue(pesel.charAt(10));
    }

    public int getBirthDateNumbers() {
        return birthDate;
    }

    public int getSerialNumbers() {
        return serial;
    }

    public int getGenderNumber() {
        return gender;
    }

    public int getControlNumber() {
        return control;
    }
}
