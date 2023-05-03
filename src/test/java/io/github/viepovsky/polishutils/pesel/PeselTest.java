package io.github.viepovsky.polishutils.pesel;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PeselTest {
    @Test
    void should_throw_exception_if_given_pesel_is_null() {
        assertThrows(NullPointerException.class, () -> new Pesel(null));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPesels")
    void should_throw_exception_if_given_pesel_is_invalid(String givenPesel) {
        assertThrows(InvalidPeselException.class, () -> new Pesel(givenPesel));
    }

    private static Stream<Arguments> provideInvalidPesels() {
        return Stream.of(
                Arguments.of("string"),
                Arguments.of("85122496612s"),
                Arguments.of("8512249661S"),
                Arguments.of("00000000000"),
                Arguments.of("11111111111"),
                Arguments.of("01016000019")
        );
    }

    @ParameterizedTest
    @MethodSource("providePesels")
    void should_return_correct_birth_date(String givenParam, LocalDate expectedDate) throws InvalidPeselException {
        var pesel = new Pesel(givenParam);
        assertEquals(expectedDate, pesel.getBirthDate());
    }

    private static Stream<Arguments> providePesels() {
        return Stream.of(
                Arguments.of("77031167334", LocalDate.of(1977, 3, 11)),
                Arguments.of("04242625931", LocalDate.of(2004, 4, 26)),
                Arguments.of("92082683499", LocalDate.of(1992, 8, 26)),
                Arguments.of("58883175997", LocalDate.of(1858, 8, 31)),
                Arguments.of("58083175993", LocalDate.of(1958, 8, 31)),
                Arguments.of("58283175999", LocalDate.of(2058, 8, 31)),
                Arguments.of("58483175995", LocalDate.of(2158, 8, 31)),
                Arguments.of("58683175991", LocalDate.of(2258, 8, 31))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "78010469227",
            "82062782227",
            "90082895388",
            "90010751823",
            "69100149787",
            "83121688322",
            "48030181861",
            "78111243865",
            "81122018287",
            "64031643742"
    })
    void should_return_gender_female(String givenPesel) throws InvalidPeselException {
        var pesel = new Pesel(givenPesel);
        assertEquals(Pesel.Gender.FEMALE.toString(), pesel.getGender());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "68060266493",
            "74040867518",
            "85092786133",
            "83032769796",
            "56090256131",
            "97061626597",
            "59092794319",
            "96033155772",
            "66020829795",
            "74040152795"
    })
    void should_return_gender_male(String givenPesel) throws InvalidPeselException {
        var pesel = new Pesel(givenPesel);
        assertEquals(Pesel.Gender.MALE.toString(), pesel.getGender());
    }

    @Test
    void should_get_correct_pesel_numbers() throws InvalidPeselException {
        var pesel = new Pesel("74040152795");

        int retrievedBirthDate = pesel.getBirthDateNumbers();
        int retrievedRandom = pesel.getSerialNumbers();
        int retrievedGender = pesel.getGenderNumber();
        int retrievedControl = pesel.getControlNumber();

        assertEquals(740401, retrievedBirthDate);
        assertEquals(527, retrievedRandom);
        assertEquals(9, retrievedGender);
        assertEquals(5, retrievedControl);
    }

    @Test
    void should_return_true_if_pesel_is_valid_and_false_if_pesel_is_invalid() {
        assertTrue(PeselValidator.isValid("78010469227"));
        assertTrue(PeselValidator.isValid("73673198930"));
        assertFalse(PeselValidator.isValid("78010469225"));
    }

    @Test
    void should_throw_exception_if_pesel_is_invalid() {
        assertThrows(InvalidPeselException.class, () -> PeselValidator.assertIsValid("78010469225"));
    }
}