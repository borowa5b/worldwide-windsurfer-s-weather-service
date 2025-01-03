package com.borowa5b.wwws.domain.exception;

import com.borowa5b.wwws.domain.exception.validation.ValidationError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationErrorException extends RuntimeException {

    private final ValidationError error;
}

