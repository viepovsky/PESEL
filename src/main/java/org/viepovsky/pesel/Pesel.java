package org.viepovsky.pesel;

import java.time.LocalDate;

public class Pesel {
    private final LocalDate birthDate;
    private final Gender gender;
    private final PeselNumbers peselNumbers;

    public Pesel(String pesel) throws InvalidPeselException {
        this(new PeselDecoder(), pesel);
    }

    private Pesel(PeselDecoder peselDecoder, String pesel) throws InvalidPeselException {
        PeselValidator.assertIsPeselValid(pesel);
        this.peselNumbers = new PeselNumbers(pesel);
        this.birthDate = peselDecoder.decodeBirthDate(pesel);
        this.gender = peselDecoder.decodeGender(pesel);
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public PeselNumbers getPeselNumbers() {
        return peselNumbers;
    }
}
