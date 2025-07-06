package com.arka.user_service.domain.exception;

public class InvalidPhoneException extends RuntimeException {
    public InvalidPhoneException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.INVALID_PHONE_FORMAT);
    }
}

