package org.viepovsky.pesel;

public class Pesel {
    private final PeselBirthDate birthDate;
    private final PeselGender gender;
    private final PeselNumbers peselNumbers;
    public Pesel (String pesel) throws InvalidPeselException {
        this(new PeselValidator(), pesel);
    }
    private Pesel(PeselValidator peselValidator, String pesel) throws InvalidPeselException {
        peselValidator.isPeselValid(pesel);
        this.peselNumbers = new PeselNumbers(pesel);
        this.birthDate = new PeselBirthDate(pesel, peselNumbers);
        this.gender = peselValidator.decodeGender(pesel);
    }

    public PeselGender getGender() {
        return gender;
    }
}
