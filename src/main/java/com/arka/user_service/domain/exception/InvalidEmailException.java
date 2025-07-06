package com.arka.user_service.domain.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.INVALID_EMAIL_FORMAT);
    }
}

