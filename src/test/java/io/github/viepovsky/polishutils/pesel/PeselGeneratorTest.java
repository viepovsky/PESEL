package io.github.viepovsky.polishutils.pesel;

import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PeselGeneratorTest {
    @RepeatedTest(100)
    void should_generate_pesel_with_given_params() throws InvalidPeselException {
        LocalDate minDate = LocalDate.of(1990, 1, 1);
        LocalDate maxDate = LocalDate.of(2010, 1, 1);
        PeselGeneratorParams.Gender gender = PeselGeneratorParams.Gender.FEMALE;

        var params = PeselGeneratorParams.builder()
                .gender(gender)
                .minDate(minDate)
                .maxDate(maxDate)
                .build();
        var peselGenerator = new PeselGenerator(params);
        String generatedPesel = peselGenerator.generatePesel();

        var pesel = new Pesel(generatedPesel);

        assertNotNull(generatedPesel);
        assertTrue(PeselValidator.isValid(generatedPesel));
        assertTrue(pesel.getBirthDate().plusDays(1).isAfter(minDate));
        assertTrue(pesel.getBirthDate().minusDays(1).isBefore(maxDate));
        assertEquals(PeselGeneratorParams.Gender.FEMALE.toString(), pesel.getGender());
    }

    @RepeatedTest(100)
    void should_generate_only_female_pesel() throws InvalidPeselException {
        var params = PeselGeneratorParams.builder()
                .gender(PeselGeneratorParams.Gender.FEMALE)
                .build();
        var generator = new PeselGenerator(params);
        String generatedPesel = generator.generatePesel();

        var pesel = new Pesel(generatedPesel);
        System.out.println(generatedPesel);
        assertNotNull(generatedPesel);
        assertTrue(PeselValidator.isValid(generatedPesel));
        assertEquals(PeselGeneratorParams.Gender.FEMALE.toString(), pesel.getGender());
    }

    @RepeatedTest(100)
    void should_generate_only_male_pesel() throws InvalidPeselException {
        var params = PeselGeneratorParams.builder()
                .gender(PeselGeneratorParams.Gender.MALE)
                .build();
        var generator = new PeselGenerator(params);
        String generatedPesel = generator.generatePesel();

        var pesel = new Pesel(generatedPesel);

        assertNotNull(generatedPesel);
        assertTrue(PeselValidator.isValid(generatedPesel));
        assertEquals(PeselGeneratorParams.Gender.MALE.toString(), pesel.getGender());
    }

    @RepeatedTest(100)
    void should_generate_pesel_with_default_params() throws InvalidPeselException {
        var params = PeselGeneratorParams.builder().build();
        var generator = new PeselGenerator(params);
        String generatedPesel = generator.generatePesel();

        var pesel = new Pesel(generatedPesel);
        assertNotNull(generatedPesel);
        assertTrue(pesel.getBirthDate().plusDays(1).isAfter(LocalDate.now().minusYears(100)));
        assertTrue(pesel.getBirthDate().minusDays(1).isBefore(LocalDate.now()));
        assertTrue(PeselValidator.isValid(generatedPesel));
    }
}
