package com.arka.user_service.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.EMAIL_ALREADY_EXISTS);
    }
}

