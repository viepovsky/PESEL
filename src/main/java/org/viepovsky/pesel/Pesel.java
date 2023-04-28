package org.viepovsky.pesel;

import java.time.LocalDate;

/**
 * The {@code Pesel} class holds information of a Polish Personal Identification Number (PESEL).
 * <p>
 * A correct PESEL has 11 digits and contains information about date of birth, gender, and a serial number.
 * The first six digits represent the date of birth in the format YYMMDD. The 7-9th digits represent the serial number,
 * and the tenth digit determines the person's gender: odd are assigned to males, even to females.
 * The last digit is a control sum number.
 * <p>
 * Upon construction, this class validates PESEL. It provides methods to decode and get gender and birthdate.
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
    private final LocalDate birthDate;
    private final Gender gender;

    public Pesel(String pesel) throws InvalidPeselException {
        this(new PeselDecoder(), pesel);
    }

    private Pesel(PeselDecoder peselDecoder, String pesel) throws InvalidPeselException {
        super(pesel);
        PeselValidator.assertIsValid(pesel);
        this.birthDate = peselDecoder.decodeBirthDate(pesel);
        this.gender = peselDecoder.decodeGender(pesel);
    }

    public String getGender() {
        return String.valueOf(gender);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    enum Gender {
        MALE,
        FEMALE
    }
}
