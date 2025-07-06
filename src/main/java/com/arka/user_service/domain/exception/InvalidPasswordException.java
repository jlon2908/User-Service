package com.arka.user_service.domain.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.PASSWORD_TOO_SHORT);
    }
}

