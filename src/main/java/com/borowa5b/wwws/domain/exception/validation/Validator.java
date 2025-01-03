package com.borowa5b.wwws.domain.exception.validation;

import com.borowa5b.wwws.domain.exception.ValidationErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;

public class Validator {

    public static void isNotNull(final String value,
                                 final String fieldName,
                                 final ValidationExceptionHandler validationExceptionHandler) {
        if (value == null) {
            validationExceptionHandler.handle(new ValidationErrorException(
                    new ValidationError(
                            "Missing value",
                            format("Field '%s' value is required", fieldName),
                            fieldName
                    )
            ));
        }
    }

    public static void isCorrectLocalDate(final String value,
                                          final String fieldName,
                                          final ValidationExceptionHandler validationExceptionHandler) {
        if (value != null && parseLocalDate(value) == null) {
            validationExceptionHandler.handle(new ValidationErrorException(
                    new ValidationError(
                            "Invalid date",
                            "Invalid format for date. Expected format: YYYY-MM-DD",
                            fieldName
                    )
            ));
        }
    }

    public static void isWithinDaysPeriodInFuture(final String value,
                                                  final String fieldName,
                                                  final int daysInFuture,
                                                  final ValidationExceptionHandler validationExceptionHandler) {
        final var localDate = parseLocalDate(value);
        final var referenceDate = LocalDate.now().plusDays(daysInFuture - 1);
        if (localDate != null && (localDate.isAfter(referenceDate) || localDate.isBefore(LocalDate.now()))) {
            validationExceptionHandler.handle(new ValidationErrorException(
                    new ValidationError(
                            "Date out of range",
                            format("Date must be within %s days from current date", daysInFuture),
                            fieldName
                    )
            ));
        }
    }

    private static LocalDate parseLocalDate(final String value) {
        if (value == null) {
            return null;
        }

        try {
            return LocalDate.parse(value);
        } catch (final DateTimeParseException exception) {
            return null;
        }
    }
}
