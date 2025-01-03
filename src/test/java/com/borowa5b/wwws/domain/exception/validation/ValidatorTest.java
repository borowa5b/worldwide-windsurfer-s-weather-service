package com.borowa5b.wwws.domain.exception.validation;

import com.borowa5b.wwws.domain.exception.ValidationErrorException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ValidatorTest {

    @Test
    void shouldNotValidateIfValueIsNull() {
        // given
        final var fieldName = "value";
        final var validationExceptionHandler = validationExceptionHandler();

        // when
        final var result = catchThrowable(() -> Validator.isNotNull(null, fieldName, validationExceptionHandler));

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException.class);

        final var errorException = (ValidationErrorException) result;
        assertThat(errorException.getError().message()).isEqualTo("Field 'value' value is required");
    }

    @Test
    void shouldNotValidateIfDateIsNotCorrect() {
        // given
        final var value = "2022-13-xs";
        final var fieldName = "date";
        final var validationExceptionHandler = validationExceptionHandler();

        // when
        final var result = catchThrowable(() -> Validator.isCorrectLocalDate(value, fieldName, validationExceptionHandler));

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException.class);

        final var errorException = (ValidationErrorException) result;
        assertThat(errorException.getError().message()).isEqualTo("Invalid format for date. Expected format: YYYY-MM-DD");
    }

    @Test
    void shouldNotValidateIfDateIsNotWithinDaysPeriodInFuture() {
        // given
        final var value = LocalDate.now().plusDays(3).toString();
        final var daysInFuture = 2;
        final var fieldName = "date";
        final var validationExceptionHandler = validationExceptionHandler();

        // when
        final var result = catchThrowable(() -> Validator.isWithinDaysPeriodInFuture(value, fieldName, daysInFuture, validationExceptionHandler));

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException.class);

        final var errorException = (ValidationErrorException) result;
        assertThat(errorException.getError().message()).isEqualTo("Date must be within 2 days from current date");
    }

    private ValidationExceptionHandler validationExceptionHandler() {
        return exception -> {
            throw exception;
        };
    }
}
