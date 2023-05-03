package io.github.viepovsky.polishutils.pesel;

import java.time.LocalDate;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * The {@code PeselGenerator} class is used to generate random Polish Personal Identification Numbers (PESEL).
 * <p>
 * You can generate a PESEL using the static method {@code generatePeselStatic()} which uses the default
 * parameters defined in the {@code PeselGeneratorParams} class. However, if you want to generate a PESEL with specific
 * parameters, you can use the {@code PeselGeneratorParams} class to specify the desired gender and date range.
 * <p>
 * Example usage of the {@code PeselGenerator} class:
 * <blockquote><pre>
 *     String generatedPesel = PeselGenerator.generatePeselStatic(); // generates random PESEL with default params
 * </pre></blockquote>
 * or with usage of {@code PeselGeneratorParams} class:
 * <blockquote><pre>
 *     LocalDate minDate = LocalDate.of(1990, 1, 1);
 *     LocalDate maxDate = LocalDate.of(2010, 1, 1);
 *     PeselGeneratorParams.Gender gender = PeselGeneratorParams.Gender.FEMALE;
 *
 *     PeselGeneratorParams params = PeselGeneratorParams.builder()
 *              .gender(gender)
 *              .minDate(minDate)
 *              .maxDate(maxDate)
 *              .build();
 *
 *     PeselGenerator peselGenerator = new PeselGenerator(params);
 *     String generatedPesel = peselGenerator.generatePesel();
 *     //generates random PESEL for a female person born between January 1, 1990 and January 1, 2010
 * </pre></blockquote>
 * Passing a {@code null} argument to a constructor will cause a {@link NullPointerException} to be thrown.
 *
 * @author Oskar Rajzner
 */
public class PeselGenerator {
    /**
     * Digits weights for calculating the control digit of the PESEL.
     */
    private static final int[] CONTROL_WEIGHTS = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
    /**
     * A random number generator used for generating PESEL numbers.
     */
    private static final Random RANDOM = new Random();
    /**
     * The gender that will be included in the generated PESEL numbers.
     */
    private static PeselGeneratorParams.Gender gender;
    /**
     * The minimum date range from which a PESEL number will be generated.
     */
    private LocalDate minDate;
    /**
     * The maximum date range to which a PESEL number will be generated.
     */
    private LocalDate maxDate;

    /**
     * Creates a new instance of the {@code PeselGenerator} class with the specified {@code PeselGeneratorParams}.
     *
     * @param params the {@code PeselGeneratorParams} object that specifies the parameters for generating the PESEL numbers.
     */
    public PeselGenerator(PeselGeneratorParams params) {
        gender = params.getGender();
        minDate = params.getMinDate();
        maxDate = params.getMaxDate();
    }

    /**
     * Generates a random PESEL number with default parameters.
     * <p>
     * The default parameters are as follows:
     * <li>Random gender</li>
     * <li>Date of birth between today's date and 100 years before today's date.</li>
     *
     * @return a randomly generated PESEL number as a string value
     */
    public static String generatePeselStatic() {
        LocalDate minDate = PeselGeneratorParams.getDefaultMinDate();
        LocalDate maxDate = PeselGeneratorParams.getDefaultMaxDate();
        return generatePesel(minDate, maxDate);
    }

    /**
     * Generates a random PESEL number with given parameters passed in {@code PeselGeneratorParams} object.
     *
     * @return a randomly generated PESEL number as a string value
     */
    public String generatePesel() {
        return generatePesel(minDate, maxDate);
    }

    /**
     * Main method to generate PESEL, it gathers all parts of PESEL and returns whole PESEL as result.
     *
     * @param minDate class field as LocalDate object
     * @param maxDate class field as LocalDate object
     * @return the PESEL number as String
     */
    private static String generatePesel(LocalDate minDate, LocalDate maxDate) {
        String birthDateDigits = getBirthDateDigit(minDate, maxDate);
        String randomSerialDigits = getRandomSerialDigits();
        String genderDigit = getGenderRandomDigit();
        String controlDigit = getControlDigit(birthDateDigits, randomSerialDigits, genderDigit);
        return birthDateDigits + randomSerialDigits + genderDigit + controlDigit;
    }

    /**
     * Method for getting a random birth date within the specified date range and encoding it to match as PESEL part.
     *
     * @param minDate the minimum date range as a LocalDate object
     * @param maxDate the maximum date range as a LocalDate object
     * @return the encoded PESEL birth date as a String
     */
    private static String getBirthDateDigit(LocalDate minDate, LocalDate maxDate) {
        LocalDate birthDate = getRandomBirthDate(minDate, maxDate);
        return encodeBirthDate(birthDate);
    }

    /**
     * Returns a random birth date within the specified date range
     *
     * @param minDate the minimum date range as a LocalDate object
     * @param maxDate the maximum date range as a LocalDate object
     * @return a random birth date within the specified range as a LocalDate object
     */
    private static LocalDate getRandomBirthDate(LocalDate minDate, LocalDate maxDate) {
        long days = DAYS.between(minDate, maxDate);
        return minDate.plusDays(RANDOM.nextLong(days + 1));
    }

    /**
     * Encodes a birth date as a PESEL birth date part.
     *
     * @param birthDate the date of birth as a LocalDate object
     * @return the encoded PESEL birth date as a String
     */
    private static String encodeBirthDate(LocalDate birthDate) {
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

    /**
     * Generates a random string of three digits to represent the serial number in the PESEL number.
     *
     * @return a randomly generated three-digit string
     */
    private static String getRandomSerialDigits() {
        return String.valueOf(RANDOM.nextInt(10)) + RANDOM.nextInt(10) + RANDOM.nextInt(10);
    }


    /**
     * Generates a random digit to represent the gender in the PESEL number. If a gender is specified,
     * the PESEL will be generated with that gender. If no gender is specified, the generated gender will be random.
     * Once a gender is generated, it is set to null so that it will be randomized on the next call to this method.
     *
     * @return the generated gender digit of the PESEL number as a string
     */
    private static String getGenderRandomDigit() {
        if (!isGenderGiven()) {
            setRandomGender();
            String genderDigit = checkGenderAndEncodeDigit();
            gender = null;
            return genderDigit;
        }
        return checkGenderAndEncodeDigit();
    }

    /**
     * Checks if the gender was specified as a parameter.
     *
     * @return true if gender was specified, false otherwise
     */
    private static boolean isGenderGiven() {
        return gender != null;
    }

    /**
     * Sets a random gender to the gender field variable.
     */
    private static void setRandomGender() {
        gender = RANDOM.nextInt(10) % 2 == 0 ? PeselGeneratorParams.Gender.FEMALE : PeselGeneratorParams.Gender.MALE;
    }

    /**
     * Checks the gender and calls the appropriate method to generate a random gender digit.
     *
     * @return the generated gender digit of the PESEL number as a string
     */
    private static String checkGenderAndEncodeDigit() {
        if (gender.equals(PeselGeneratorParams.Gender.FEMALE)) {
            return encodeRandomFemaleDigit();
        } else {
            return encodeRandomMaleDigit();
        }
    }

    /**
     * Encodes a random female digit.
     *
     * @return the encoded random female digit as a String
     */
    private static String encodeRandomFemaleDigit() {
        return String.valueOf(RANDOM.nextInt(5) * 2);
    }

    /**
     * Encodes a random male digit.
     *
     * @return the encoded random male digit as a String
     */
    private static String encodeRandomMaleDigit() {
        return String.valueOf(RANDOM.nextInt(5) * 2 + 1);
    }

    /**
     * Calculates and returns the correct control digit for a generated PESEL number.
     *
     * @param birthDateDigits the birth date digits of the PESEL as a string
     * @param randomSerialDigits the random serial digits of the PESEL as a string
     * @param genderDigit the gender digit of the PESEL as a string
     * @return the calculated control digit of the PESEL as a string
     */
    private static String getControlDigit(String birthDateDigits, String randomSerialDigits, String genderDigit) {
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
