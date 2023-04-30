package com.viepovsky.pesel;

import java.time.LocalDate;

/**
 * The {@code Pesel} class holds information of a Polish Personal Identification Number (PESEL).
 * <p>
 * A correct PESEL has 11 digits and contains information about the date of birth and gender.
 * It has the form of YYMMDDZZZXQ, where YYMMDD (digits 1-6) represents the date of birth, ZZZ (digits 7-9)
 * represents the unique serial number, and X (digit 10) determines the person's gender
 * (odd for males, even for females). The Q (digit 11) is a control sum number that verifies whether
 * a given PESEL is correct or not.
 * <p>
 * Upon construction, this class validates PESEL, it provides methods to decode and get gender and birthdate.
 * You can also get upper specified digits as ints by calling the class get methods.
 * <p>
 * Example usage of the {@code Pesel} class:
 * <blockquote><pre>
 *     Pesel pesel = new Pesel("92082683499");
 * </pre></blockquote>
 *     and then you can access the PESEL values:
 * <blockquote><pre>
 *     String gender = pesel.getGender();
 *     LocalDate birthDate = pesel.getBirthDate();
 *     int serialNumbers = pesel.getSerialNumbers();
 * </pre></blockquote>
 * Remember to catch exceptions upon construction. Passing a {@code null} argument to a constructor will
 * cause a {@link NullPointerException} to be thrown. Passing an invalid PESEL to a constructor will
 * cause an {@link InvalidPeselException} to be thrown.
 *
 * @author Oskar Rajzner
 */
public class Pesel extends PeselNumbers {
    /**
     * The date of birth decoded from the given PESEL.
     */
    private final LocalDate birthDate;
    /**
     * The gender decoded from the given PESEL.
     */
    private final Gender gender;

    /**
     * Creates a new {@code Pesel} object from the specified PESEL number.
     * Upon creation, the constructor validates the PESEL.
     *
     * @param pesel the PESEL number
     * @throws InvalidPeselException if the given PESEL is invalid
     */
    public Pesel(String pesel) throws InvalidPeselException {
        this(new PeselDecoder(), pesel);
    }

    /**
     * Private constructor of {@code Pesel} class. Creates a {@code PeselNumber} object,
     * validates given PESEL, decodes and stores date of birth and gender.
     *
     * @param peselDecoder an instance of the {@code PeselDecoder} class
     * @param pesel the PESEL number
     * @throws InvalidPeselException if the given PESEL is invalid
     */
    private Pesel(PeselDecoder peselDecoder, String pesel) throws InvalidPeselException {
        super(pesel);
        PeselValidator.assertIsValid(pesel);
        this.birthDate = peselDecoder.decodeBirthDate(pesel);
        this.gender = peselDecoder.decodeGender(pesel);
    }

    /**
     * Returns the decoded gender of the given PESEL number.
     *
     * @return the gender as a String
     */
    public String getGender() {
        return String.valueOf(gender);
    }
    /**
     * Returns decoded date of birth of given PESEL number.
     *
     * @return the date of birth as a LocalDate object
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Gender of the person's PESEL
     */
    enum Gender {
        MALE,
        FEMALE
    }
}
