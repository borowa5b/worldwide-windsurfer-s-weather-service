package com.borowa5b.wwws.domain.exception;

import com.borowa5b.wwws.domain.exception.validation.ValidationError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {

    private final List<ValidationError> errors;
}
