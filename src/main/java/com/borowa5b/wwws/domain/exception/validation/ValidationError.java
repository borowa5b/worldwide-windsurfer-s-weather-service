package com.borowa5b.wwws.domain.exception.validation;

public record ValidationError(String title, String message, String fieldName) {
}
