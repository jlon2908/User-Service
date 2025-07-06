package com.arka.user_service.domain.exception;

public class InvalidFullNameException extends RuntimeException {
    public InvalidFullNameException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.INVALID_FULLNAME_FORMAT);
    }
}

