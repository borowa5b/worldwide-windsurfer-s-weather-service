package com.borowa5b.wwws.domain.exception.validation;

import com.borowa5b.wwws.domain.exception.ValidationErrorException;

public interface ValidationExceptionHandler {

    void handle(final ValidationErrorException exception);
}
