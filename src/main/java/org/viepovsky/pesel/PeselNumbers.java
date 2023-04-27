package org.viepovsky.pesel;

class PeselNumbers {
    private final int[] birthDateNumbers;
    private final int[] randomNumbers;
    private final int genderNumber;
    private final int controlNumber;
    PeselNumbers(String pesel) {
        birthDateNumbers = addBirthDateNumbers(pesel.substring(0,6));
        randomNumbers = addRandomNumbers(pesel.substring(6,9));
        genderNumber = Character.getNumericValue(pesel.charAt(9));
        controlNumber = Character.getNumericValue(pesel.charAt(10));
    }

    private int[] addBirthDateNumbers(String peselSubstring){
        int[] numbers = new int[6];
        for(int i = 0; i < 6; i++) {
            numbers[i] = Character.getNumericValue(peselSubstring.charAt(i));
        }
        return numbers;
    }

    private int[] addRandomNumbers(String peselSubstring) {
        int[] numbers = new int[3];
        for(int i = 0; i < 3; i++) {
            numbers[i] = Character.getNumericValue(peselSubstring.charAt(i));
        }
        return numbers;
    }

    public int[] getBirthDateNumbers() {
        return birthDateNumbers;
    }

    public int[] getRandomNumbers() {
        return randomNumbers;
    }

    public int getGenderNumber() {
        return genderNumber;
    }

    public int getControlNumber() {
        return controlNumber;
    }
}
