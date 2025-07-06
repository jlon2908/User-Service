package com.arka.user_service.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.USER_NOT_FOUND);
    }
}

