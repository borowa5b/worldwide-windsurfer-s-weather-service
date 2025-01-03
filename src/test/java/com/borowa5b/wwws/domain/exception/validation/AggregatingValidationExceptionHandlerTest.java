package com.borowa5b.wwws.domain.exception.validation;

import com.borowa5b.wwws.domain.exception.ValidationErrorException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AggregatingValidationExceptionHandlerTest {

    @Test
    void shouldHandleValidationErrorException() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();
        final var validationException = new ValidationErrorException(new ValidationError("Title", "Message", "Field"));

        // when
        exceptionHandler.handle(validationException);

        // then
        assertThat(exceptionHandler.getErrors()).hasSize(1)
                .containsExactly(validationException.getError());
    }

    @Test
    void shouldCheckIfHasError() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();
        final var validationException = new ValidationErrorException(new ValidationError("Title", "Message", "Field"));
        exceptionHandler.handle(validationException);

        // when
        final var result = exceptionHandler.hasErrors();

        // then
        assertThat(result).isTrue();
    }
}