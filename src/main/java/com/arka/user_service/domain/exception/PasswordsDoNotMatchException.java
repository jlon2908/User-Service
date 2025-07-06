package com.arka.user_service.domain.exception;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.PASSWORDS_DO_NOT_MATCH);
    }
}

