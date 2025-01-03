package com.borowa5b.wwws.infrastructure;

import com.borowa5b.wwws.domain.exception.DomainException;
import com.borowa5b.wwws.domain.exception.ValidationException;
import com.borowa5b.wwws.domain.exception.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RestErrorHandlerTest {

    @Test
    void shouldHandleUnexpectedErrorException() {
        // given
        final var title = "Unexpected error";
        final var message = "Unexpected error occurred";
        final var exception = new RuntimeException(message);
        final var restErrorHandler = new RestErrorHandler();

        // when
        final var result = restErrorHandler.handle(exception);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        final var problem = result.getBody();
        assertThat(problem.getStatus().getStatusCode()).isEqualTo(result.getStatusCode().value());
        assertThat(problem.getTitle()).isEqualTo(title);
        assertThat(problem.getDetail()).isEqualTo(message);
    }

    @Test
    void shouldHandleHttpMediaTypeException() {
        // given
        final var title = "Unexpected error";
        final var exception = new HttpMediaTypeNotSupportedException(MediaType.APPLICATION_ATOM_XML, new ArrayList<>());
        final var restErrorHandler = new RestErrorHandler();

        // when
        final var result = restErrorHandler.handle(exception);

        // then
        assertThat(result.getStatusCode().value()).isEqualTo(exception.getBody().getStatus());

        final var problem = result.getBody();
        assertThat(problem.getStatus().getStatusCode()).isEqualTo(result.getStatusCode().value());
        assertThat(problem.getTitle()).isEqualTo(title);
        assertThat(problem.getDetail()).isEqualTo(exception.getBody().getDetail());
    }


    @Test
    void shouldHandleValidationException() {
        // given
        final var title = "Validation errors";
        final var message = "Validation errors occurred";
        final var validationError1 = new ValidationError(
                "Field has invalid value",
                "Field `field1` must start with `F1`",
                "field1"
        );
        final var validationError2 = new ValidationError(
                "Field has invalid value",
                "Field `field2` must start with `F2`",
                "field2"
        );
        final var errors = Arrays.asList(validationError1, validationError2);
        final var exception = new ValidationException(errors);
        final var restErrorHandler = new RestErrorHandler();

        // when
        final var result = restErrorHandler.handle(exception);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        final var problem = result.getBody();
        assertThat(problem.getStatus().getStatusCode()).isEqualTo(result.getStatusCode().value());
        assertThat(problem.getTitle()).isEqualTo(title);
        assertThat(problem.getDetail()).isEqualTo(message);

        final var problemErrors = ((List<?>) (problem.getParameters().get("errors")));
        assertThat(problemErrors).hasSize(2);
        assertThat(problemErrors.get(0)).isEqualTo(validationError1);
        assertThat(problemErrors.get(1)).isEqualTo(validationError2);
    }

    @Test
    void shouldHandleDomainException() {
        // given
        final var title = "Domain error";
        final var message = "Domain error occurred";
        final var status = Status.INTERNAL_SERVER_ERROR;
        final var exception = new DomainException(status, message);
        final var restErrorHandler = new RestErrorHandler();

        // when
        final var result = restErrorHandler.handle(exception);

        // then
        assertThat(result.getStatusCode().value()).isEqualTo(status.getStatusCode());

        final var problem = result.getBody();
        assertThat(problem.getStatus().getStatusCode()).isEqualTo(result.getStatusCode().value());
        assertThat(problem.getTitle()).isEqualTo(title);
        assertThat(problem.getDetail()).isEqualTo(message);
    }
}
