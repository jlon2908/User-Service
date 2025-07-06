package com.arka.user_service.domain.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.USERNAME_ALREADY_EXISTS);
    }
}

