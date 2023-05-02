package io.github.viepovsky.polishutils.pesel;

import java.time.LocalDate;

public class PeselGeneratorParams {
    private static final LocalDate DEFAULT_MIN_DATE = LocalDate.of(1800,1,1);
    private static final LocalDate DEFAULT_MAX_DATE = LocalDate.of(2299, 12, 31);
    private Gender gender;
    private LocalDate minDate = DEFAULT_MIN_DATE;
    private LocalDate maxDate = DEFAULT_MAX_DATE;

    private PeselGeneratorParams(Builder builder) {
        gender = builder.gender;
        if (builder.minDate != null) minDate = builder.minDate;
        if (builder.maxDate != null) maxDate = builder.maxDate;
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
            this.minDate = minDate;
            return (this);
        }

        public Builder maxDate(LocalDate maxDate) {
            this.maxDate = maxDate;
            return (this);
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
