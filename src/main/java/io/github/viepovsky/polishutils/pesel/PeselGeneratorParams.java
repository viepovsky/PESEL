package io.github.viepovsky.polishutils.pesel;

import java.time.LocalDate;

public class PeselGeneratorParams {
    private static final LocalDate MIN_DATE_POSSIBLE = LocalDate.of(1800, 1, 1);
    private static final LocalDate MAX_DATE_POSSIBLE = LocalDate.of(2299, 12, 31);
    private static final LocalDate DEFAULT_MIN_DATE = LocalDate.now().minusYears(100);
    private static final LocalDate DEFAULT_MAX_DATE = LocalDate.now();
    private Gender gender;
    private LocalDate minDate;
    private LocalDate maxDate;

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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Gender gender;
        private LocalDate minDate;
        private LocalDate maxDate;

        public Builder gender(Gender gender) {
            this.gender = gender;
            return (this);
        }

        public Builder minDate(LocalDate minDate) {
            if (minDate.isBefore(MAX_DATE_POSSIBLE.plusDays(1)) && minDate.isAfter(MIN_DATE_POSSIBLE.minusDays(1))) {
                this.minDate = minDate;
                return (this);
            } else {
                throw new IllegalArgumentException("Min and max dates should be between: " + MIN_DATE_POSSIBLE + " and " + MAX_DATE_POSSIBLE);
            }
        }

        public Builder maxDate(LocalDate maxDate) {
            if (maxDate.isBefore(MAX_DATE_POSSIBLE.plusDays(1)) && maxDate.isAfter(MIN_DATE_POSSIBLE.minusDays(1))) {
                this.maxDate = maxDate;
                return (this);
            } else {
                throw new IllegalArgumentException("Min and max dates should be between: " + MIN_DATE_POSSIBLE + " and " + MAX_DATE_POSSIBLE);
            }
        }

        public PeselGeneratorParams build() {
            return new PeselGeneratorParams(this);
        }
    }

    Gender getGender() {
        return gender;
    }

    LocalDate getMinDate() {
        return minDate;
    }

    LocalDate getMaxDate() {
        return maxDate;
    }

    public enum Gender {
        MALE,
        FEMALE
    }
}
