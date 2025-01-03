package com.borowa5b.wwws.domain.exception.validation;

import com.borowa5b.wwws.domain.exception.ValidationErrorException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AggregatingValidationExceptionHandler implements ValidationExceptionHandler {

    private final List<ValidationError> errors = new ArrayList<>();

    @Override
    public void handle(final ValidationErrorException exception) {
        errors.add(exception.getError());
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
