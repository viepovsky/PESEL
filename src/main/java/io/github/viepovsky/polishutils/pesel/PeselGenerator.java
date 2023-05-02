package io.github.viepovsky.polishutils.pesel;

import java.time.LocalDate;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

public class PeselGenerator {
    private static final int[] CONTROL_WEIGHTS = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
    private static final LocalDate DEFAULT_MIN_DATE = LocalDate.now().minusYears(100);
    private static final LocalDate DEFAULT_MAX_DATE = LocalDate.now();
    private static final Random RANDOM = new Random();
    private PeselGeneratorParams.Gender gender;
    private LocalDate minDate;
    private LocalDate maxDate;

    public PeselGenerator(PeselGeneratorParams params) {
        gender = params.getGender();
        validateAndChangeInputGender();
        minDate = params.getMinDate() != null ? params.getMinDate() : DEFAULT_MIN_DATE;
        maxDate = params.getMaxDate() != null ? params.getMaxDate() : DEFAULT_MAX_DATE;
        validateAndChangeInputDates();
    }

    private void validateAndChangeInputGender() {
        if (gender == null) {
            gender = RANDOM.nextInt(10) % 2 == 0 ? PeselGeneratorParams.Gender.FEMALE : PeselGeneratorParams.Gender.MALE;
        }
    }

    private void validateAndChangeInputDates() {
        if (minDate.isAfter(maxDate)) {
            LocalDate tempDate = minDate;
            minDate = maxDate;
            maxDate = tempDate;
        }
    }

    public String generatePesel() {
        String birthDateDigits = getBirthDateDigit();
        String randomSerialDigits = getRandomSerialDigits();
        String genderDigit = getRandomGenderDigit();
        String controlDigit = getControlDigit(birthDateDigits, randomSerialDigits, genderDigit);
        return birthDateDigits + randomSerialDigits + genderDigit + controlDigit;
    }

    private String getBirthDateDigit() {
        LocalDate birthDate = getRandomBirthDate(minDate, maxDate);
        return encodeBirthDate(birthDate);
    }

    String encodeBirthDate(LocalDate birthDate) {
        int year = birthDate.getYear();
        int month = birthDate.getMonthValue();
        int day = birthDate.getDayOfMonth();

        switch (year / 100) {
            case 18 -> month += 80;
            case 20 -> month += 20;
            case 21 -> month += 40;
            case 22 -> month += 60;
        }

        String encodedBirthDate = "";
        if (String.valueOf(year % 100).length() == 1) {
            encodedBirthDate += "0" + year % 100;
        } else {
            encodedBirthDate += year % 100;
        }
        if (String.valueOf(month).length() == 1) {
            encodedBirthDate += "0" + month;
        } else {
            encodedBirthDate += month;
        }
        if (String.valueOf(day).length() == 1) {
            encodedBirthDate += "0" + day;
        } else {
            encodedBirthDate += day;
        }
        return encodedBirthDate;
    }

    private LocalDate getRandomBirthDate(LocalDate minDate, LocalDate maxDate) {
        long days = DAYS.between(minDate, maxDate);
        return minDate.plusDays(RANDOM.nextLong(days + 1));
    }

    private String getRandomSerialDigits() {
        return String.valueOf(RANDOM.nextInt(10)) + RANDOM.nextInt(10) + RANDOM.nextInt(10);
    }

    private String getRandomGenderDigit() {
        if (gender.equals(PeselGeneratorParams.Gender.FEMALE)) {
            return encodeRandomFemaleDigit();
        } else {
            return encodeRandomMaleDigit();
        }
    }

    private String encodeRandomFemaleDigit() {
        return String.valueOf(RANDOM.nextInt(5) * 2);
    }

    private String encodeRandomMaleDigit() {
        return String.valueOf(RANDOM.nextInt(5) * 2 + 1);
    }

    private String getControlDigit(String birthDateDigits, String randomSerialDigits, String genderDigit) {
        String pesel = birthDateDigits + randomSerialDigits + genderDigit;
        int controlSum = 0;
        for (int i = 0; i < 10; i++) {
            int multipliedNumber = CONTROL_WEIGHTS[i] * Character.getNumericValue(pesel.charAt(i));
            if (multipliedNumber >= 10) {
                controlSum += Character.getNumericValue(String.valueOf(multipliedNumber).charAt(1));
            } else {
                controlSum += multipliedNumber;
            }
        }
        controlSum %= 10;
        if (controlSum == 0) {
            return "0";
        } else {
            return String.valueOf(10 - controlSum);
        }
    }
}
