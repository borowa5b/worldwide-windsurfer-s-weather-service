package com.borowa5b.wwws.infrastructure;

import com.borowa5b.wwws.domain.exception.DomainException;
import com.borowa5b.wwws.domain.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.logging.Logger;

import static java.lang.String.format;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = Logger.getLogger(RestErrorHandler.class.getSimpleName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handle(final Exception exception) {
        final var status = resolveStatus(exception);
        final var message = resolveMessage(exception);
        final var problem = Problem.builder()
                .withType(URI.create("wwws/unexpected-error"))
                .withTitle("Unexpected error")
                .withDetail(message)
                .withStatus(status)
                .build();
        LOGGER.warning(format("Unexpected error occurred: %s", exception));
        return ResponseEntity.status(problem.getStatus().getStatusCode()).body(problem);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Problem> handle(final ValidationException exception) {
        final var problem = Problem.builder()
                .withType(URI.create("wwws/validation-errors"))
                .withTitle("Validation errors")
                .withDetail("Validation errors occurred")
                .withStatus(Status.BAD_REQUEST)
                .with("errors", exception.getErrors())
                .build();
        return ResponseEntity.status(problem.getStatus().getStatusCode()).body(problem);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Problem> handle(final DomainException exception) {
        final var problem = Problem.builder()
                .withType(URI.create("wwws/domain-error"))
                .withTitle("Domain error")
                .withDetail(exception.getMessage())
                .withStatus(exception.getStatus())
                .build();
        return ResponseEntity.status(problem.getStatus().getStatusCode()).body(problem);
    }

    private String resolveMessage(final Exception exception) {
        if (exception instanceof HttpMediaTypeException ex) {
            return ex.getBody().getDetail();
        }
        return exception.getMessage();
    }

    private Status resolveStatus(final Exception exception) {
        if (exception instanceof HttpMediaTypeException ex) {
            return Status.valueOf(ex.getBody().getStatus());
        }
        return Status.INTERNAL_SERVER_ERROR;
    }
}
