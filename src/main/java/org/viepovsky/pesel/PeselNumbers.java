package org.viepovsky.pesel;

class PeselNumbers {
    private final int birthDate;
    private final int random;
    private final int gender;
    private final int control;

    PeselNumbers(String pesel) {
        birthDate = Integer.parseInt(pesel.substring(0, 6));
        random = Integer.parseInt(pesel.substring(6, 9));
        gender = Character.getNumericValue(pesel.charAt(9));
        control = Character.getNumericValue(pesel.charAt(10));
    }

    public int getBirthDate() {
        return birthDate;
    }

    public int getRandom() {
        return random;
    }

    public int getGender() {
        return gender;
    }

    public int getControl() {
        return control;
    }
}
