package com.viepovsky.pesel;

import java.time.LocalDate;

/**
 * The {@code PeselDecoder} class decodes the date of birth and gender from PESEL
 * (Polish Personal Identification Number). This class is package-private
 * and is not intended for use outside of the org.viepovsky.pesel package.
 *
 * @author Oskar Rajzner
 */
class PeselDecoder {
    /**
     * Decodes the date of birth from given PESEL.
     * @param pesel the PESEL number
     * @return the date of birth as a LocalDate object
     */
    LocalDate decodeBirthDate(String pesel) {
        int year = 1900;
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        int monthFirstDigit = Character.getNumericValue(pesel.charAt(2));
        switch (monthFirstDigit) {
            case 8, 9 -> {
                month -= 80;
                year = 1800;
            }
            case 2, 3 -> {
                month -= 20;
                year = 2000;
            }
            case 4, 5 -> {
                month -= 40;
                year = 2100;
            }
            case 6, 7 -> {
                month -= 60;
                year = 2200;
            }
        }

        year += Integer.parseInt(pesel.substring(0, 2));
        return LocalDate.of(year, month, day);
    }

    /**
     * Decodes the gender from given PESEL.
     * @param pesel the PESEL number
     * @return the gender as a Pese.Gender enum
     */
    Pesel.Gender decodeGender(String pesel) {
        int genderDigit = Character.getNumericValue(pesel.charAt(9));
        return genderDigit % 2 == 0 ? Pesel.Gender.FEMALE : Pesel.Gender.MALE;
    }
}
