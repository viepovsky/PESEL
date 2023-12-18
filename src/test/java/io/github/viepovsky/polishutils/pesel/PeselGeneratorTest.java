package io.github.viepovsky.polishutils.pesel;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PeselGeneratorTest {
    @ParameterizedTest
    @MethodSource("providePesels")
    void should_generate_pesel_with_given_params(LocalDate minDate, LocalDate maxDate) {
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
        assertTrue(PeselValidator.isPeselValid(generatedPesel));
        assertTrue(pesel.getBirthDate().plusDays(1).isAfter(minDate));
        assertTrue(pesel.getBirthDate().minusDays(1).isBefore(maxDate));
        assertEquals(PeselGeneratorParams.Gender.FEMALE.toString(), pesel.getGender());
    }

    private static Stream<Arguments> providePesels() {
        return Stream.of(
                Arguments.of(LocalDate.of(2200, 1, 1), LocalDate.of(2299, 12, 31)),
                Arguments.of(LocalDate.of(2100, 1, 1), LocalDate.of(2199, 12, 31)),
                Arguments.of(LocalDate.of(2000, 1, 1), LocalDate.of(2099, 12, 31)),
                Arguments.of(LocalDate.of(1900, 1, 1), LocalDate.of(1999, 12, 31)),
                Arguments.of(LocalDate.of(1800, 1, 1), LocalDate.of(1899, 12, 31))
        );
    }

    @RepeatedTest(100)
    void should_generate_only_female_pesel() {
        var params = PeselGeneratorParams.builder()
                .gender(PeselGeneratorParams.Gender.FEMALE)
                .build();
        var generator = new PeselGenerator(params);
        String generatedPesel = generator.generatePesel();

        var pesel = new Pesel(generatedPesel);
        System.out.println(generatedPesel);
        assertNotNull(generatedPesel);
        assertTrue(PeselValidator.isPeselValid(generatedPesel));
        assertEquals(PeselGeneratorParams.Gender.FEMALE.toString(), pesel.getGender());
    }

    @RepeatedTest(100)
    void should_generate_only_male_pesel() {
        var params = PeselGeneratorParams.builder()
                .gender(PeselGeneratorParams.Gender.MALE)
                .build();
        var generator = new PeselGenerator(params);
        String generatedPesel = generator.generatePesel();

        var pesel = new Pesel(generatedPesel);

        assertNotNull(generatedPesel);
        assertTrue(PeselValidator.isPeselValid(generatedPesel));
        assertEquals(PeselGeneratorParams.Gender.MALE.toString(), pesel.getGender());
    }

    @RepeatedTest(100)
    void should_generate_pesel_with_default_params() {
        var params = PeselGeneratorParams.builder().build();
        var generator = new PeselGenerator(params);
        String generatedPesel = generator.generatePesel();

        var pesel = new Pesel(generatedPesel);
        assertNotNull(generatedPesel);
        assertTrue(pesel.getBirthDate().plusDays(1).isAfter(LocalDate.now().minusYears(100)));
        assertTrue(pesel.getBirthDate().minusDays(1).isBefore(LocalDate.now()));
        assertTrue(PeselValidator.isPeselValid(generatedPesel));
    }

    @Test
    void should_throw_exception_if_given_minDate_is_invalid() {
        LocalDate minDate = LocalDate.of(2300, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> PeselGeneratorParams.builder().minDate(minDate));
    }

    @Test
    void should_throw_exception_if_given_maxDate_is_invalid() {
        LocalDate maxDate = LocalDate.of(1799, 12, 31);
        assertThrows(IllegalArgumentException.class, () -> PeselGeneratorParams.builder().maxDate(maxDate));
    }

    @Test
    void should_swap_min_and_max_date_if_put_in_incorrect_way() {
        LocalDate maxDate = LocalDate.of(1990, 1, 1);
        LocalDate minDate = LocalDate.of(2010, 1, 1);

        var params = PeselGeneratorParams.builder()
                .minDate(minDate)
                .maxDate(maxDate)
                .build();

        assertEquals(minDate, params.getMaxDate());
        assertEquals(maxDate, params.getMinDate());
    }

    @RepeatedTest(100)
    void should_generate_correct_pesel_if_only_min_param_is_given() {
        LocalDate minDate = LocalDate.of(2200, 1, 1);
        var params = PeselGeneratorParams.builder()
                .minDate(minDate)
                .build();
        var peselGenerator = new PeselGenerator(params);
        String generatedPesel = peselGenerator.generatePesel();

        var pesel = new Pesel(generatedPesel);

        assertNotNull(generatedPesel);
        assertTrue(PeselValidator.isPeselValid(generatedPesel));
        assertTrue(pesel.getBirthDate().plusDays(1).isAfter(minDate));
        assertTrue(pesel.getBirthDate().minusDays(1).isBefore(LocalDate.of(2299, 12, 31)));
    }

    @RepeatedTest(100)
    void should_generate_correct_pesel_if_only_max_param_is_given() {
        LocalDate maxDate = LocalDate.of(2200, 1, 1);
        var params = PeselGeneratorParams.builder()
                .maxDate(maxDate)
                .build();
        var peselGenerator = new PeselGenerator(params);
        String generatedPesel = peselGenerator.generatePesel();

        var pesel = new Pesel(generatedPesel);

        assertNotNull(generatedPesel);
        assertTrue(PeselValidator.isPeselValid(generatedPesel));
        assertTrue(pesel.getBirthDate().plusDays(1).isAfter(LocalDate.of(1800, 1, 1)));
        assertTrue(pesel.getBirthDate().minusDays(1).isBefore(maxDate));
    }

    @Test
    void should_generate_pesel_with_given_date() {
        LocalDate minDate = LocalDate.of(1955, 11, 22);
        LocalDate maxDate = LocalDate.of(1955, 11, 22);
        var params = PeselGeneratorParams.builder()
                .maxDate(maxDate)
                .minDate(minDate)
                .build();
        var peselGenerator = new PeselGenerator(params);
        String generatedPesel = peselGenerator.generatePesel();

        var pesel = new Pesel(generatedPesel);

        assertNotNull(generatedPesel);
        assertTrue(PeselValidator.isPeselValid(generatedPesel));
        assertEquals(pesel.getBirthDate(), minDate);
    }

    @RepeatedTest(100)
    void should_generate_pesel_from_static_method() {
        String generatedPesel = PeselGenerator.generatePeselStatic();

        assertTrue(PeselValidator.isPeselValid(generatedPesel));
    }
}
