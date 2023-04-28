package org.viepovsky.pesel;

import java.time.LocalDate;

public class Pesel extends PeselNumbers{
    private final LocalDate birthDate;
    private final Gender gender;

    public Pesel(String pesel) throws InvalidPeselException {
        this(new PeselDecoder(), pesel);
    }

    private Pesel(PeselDecoder peselDecoder, String pesel) throws InvalidPeselException {
        super(pesel);
        this.birthDate = peselDecoder.decodeBirthDate(pesel);
        this.gender = peselDecoder.decodeGender(pesel);
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    enum Gender {
        MALE,
        FEMALE
    }
}
