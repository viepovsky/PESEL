package io.github.viepovsky.polishutils.pesel;

import java.time.LocalDate;

/**
 * The {@code PeselGeneratorParams} class provides options for generating more specific random PESEL numbers.
 * By using the builder, you can choose the minimum and maximum birth date range, as well as the gender of the
 * generated PESELs.
 * <p>
 * To use the {@code PeselGeneratorParams} class, first create instances of {@code LocalDate} to specify the
 * minimum and maximum birthdays. Then, create an instance of {@code PeselGeneratorParams.Builder} and
 * set the desired options using the available methods. Finally, call the {@code build()} method to create
 * a new instance of {@code PeselGeneratorParams} with the specified options. You can use any combination of
 * the available parameters or none at all.
 * <p>
 * Example usage of the {@code PeselGeneratorParams} class:
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
 * </pre></blockquote>
 * Passing invalid date range will cause a {@link IllegalArgumentException} to be thrown.
 *
 * @author Oskar Rajzner
 * @see PeselGenerator
 */
public class PeselGeneratorParams {

    /**
     * The earliest possible date from which a PESEL number can be generated.
     */
    private static final LocalDate MIN_DATE_POSSIBLE = LocalDate.of(1800, 1, 1);

    /**
     * The latest possible date to which a PESEL number can be generated.
     */
    private static final LocalDate MAX_DATE_POSSIBLE = LocalDate.of(2299, 12, 31);

    /**
     * The default earliest date from which a PESEL number can be generated,
     * which is 100 years before the current date.
     */
    private static final LocalDate DEFAULT_MIN_DATE = LocalDate.now().minusYears(100);

    /**
     * The default latest date to which a PESEL number can be generated, which is the current date.
     */
    private static final LocalDate DEFAULT_MAX_DATE = LocalDate.now();

    /**
     * The gender that will be included in the generated PESEL numbers.
     */
    private final Gender gender;

    /**
     * The earliest date from which a PESEL number will be generated.
     */
    private LocalDate minDate;

    /**
     * The latest date to which a PESEL number will be generated.
     */
    private LocalDate maxDate;

    /**
     * Constructs a new PeselGeneratorParams object using the Builder pattern.
     * It validates input dates values.
     *
     * @param builder The Builder object containing the parameters to be used for generating PESEL numbers.
     */
    private PeselGeneratorParams(Builder builder) {
        gender = builder.gender;
        if (builder.minDate != null && builder.maxDate != null) {
            minDate = builder.minDate;
            maxDate = builder.maxDate;
        } else if (builder.minDate == null && builder.maxDate == null) {
            minDate = DEFAULT_MIN_DATE;
            maxDate = DEFAULT_MAX_DATE;
        } else {
            minDate = builder.minDate == null ? MIN_DATE_POSSIBLE : builder.minDate;
            maxDate = builder.maxDate == null ? MAX_DATE_POSSIBLE : builder.maxDate;
        }
        if (minDate.isAfter(maxDate)) {
            LocalDate tempDate = minDate;
            minDate = maxDate;
            maxDate = tempDate;
        }
    }

    /**
     * Returns a new instance of the Builder class, which can be used to set and build
     * {@code PeselGeneratorParams} object.
     *
     * @return a new instance of {@code PeselGeneratorParams} Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for creating instances of {@code PeselGeneratorParams}.
     * Allows for setting the gender and date range of the generated PESEL numbers.
     */
    public static final class Builder {
        /**
         * The gender that will be included in the generated PESEL numbers.
         */
        private Gender gender;
        /**
         * The earliest date from which a PESEL number will be generated.
         */
        private LocalDate minDate;
        /**
         * The latest date to which a PESEL number will be generated.
         */
        private LocalDate maxDate;

        /**
         * Sets the gender to be included in the generated PESEL numbers.
         *
         * @param gender provided as {@code PeselGeneratorParams.Gender}
         * @return this builder with the gender set
         */
        public Builder gender(Gender gender) {
            this.gender = gender;
            return (this);
        }

        /**
         * Sets the earliest date from which a PESEL number will be generated.
         *
         * @param minDate the earliest date as a {@code LocalDate} object
         * @return this builder with the earliest date set
         * @throws IllegalArgumentException if the provided date is after {@code MAX_DATE_POSSIBLE}
         *                                  or before {@code MIN_DATE_POSSIBLE}
         */
        public Builder minDate(LocalDate minDate) {
            if (minDate.isBefore(MAX_DATE_POSSIBLE.plusDays(1)) && minDate.isAfter(MIN_DATE_POSSIBLE.minusDays(1))) {
                this.minDate = minDate;
                return (this);
            } else {
                throw new IllegalArgumentException("Min and max dates should be between: " + MIN_DATE_POSSIBLE + " and " + MAX_DATE_POSSIBLE);
            }
        }

        /**
         * Sets the latest date to which a PESEL number will be generated.
         *
         * @param maxDate the latest date as a {@code LocalDate} object
         * @return this builder with the latest date set
         * @throws IllegalArgumentException if the provided date is after {@code MAX_DATE_POSSIBLE}
         *                                  or before {@code MIN_DATE_POSSIBLE}
         */
        public Builder maxDate(LocalDate maxDate) {
            if (maxDate.isBefore(MAX_DATE_POSSIBLE.plusDays(1)) && maxDate.isAfter(MIN_DATE_POSSIBLE.minusDays(1))) {
                this.maxDate = maxDate;
                return (this);
            } else {
                throw new IllegalArgumentException("Min and max dates should be between: " + MIN_DATE_POSSIBLE + " and " + MAX_DATE_POSSIBLE);
            }
        }

        /**
         * Builds a new instance of {@code PeselGeneratorParams} with the specified parameters.
         *
         * @return a new instance of {@code PeselGeneratorParams}
         */
        public PeselGeneratorParams build() {
            return new PeselGeneratorParams(this);
        }
    }

    /**
     * Returns the gender that will be included in the generated PESEL numbers.
     *
     * @return the gender as {@code PeselGeneratorParams.Gender}
     */
    Gender getGender() {
        return gender;
    }

    /**
     * Returns the minimal date range set to create PESEL.
     *
     * @return minimal date range set as LocalDate object
     */
    LocalDate getMinDate() {
        return minDate;
    }

    /**
     * Returns the maximal date range set to create PESEL.
     *
     * @return maximal date range set as LocalDate object
     */
    LocalDate getMaxDate() {
        return maxDate;
    }

    /**
     * Returns the default minimal date range to create PESEL.
     *
     * @return default minimal date range as LocalDate object
     */
    static LocalDate getDefaultMinDate() {
        return DEFAULT_MIN_DATE;
    }

    /**
     * Returns the default maximal date range to create PESEL.
     *
     * @return default maximal date range as LocalDate object
     */
    static LocalDate getDefaultMaxDate() {
        return DEFAULT_MAX_DATE;
    }

    /**
     * An enumeration representing the gender that will be included in the generated PESEL numbers.
     */
    public enum Gender {
        MALE,
        FEMALE
    }
}
