package com.arka.user_service.domain.exception;

public class InvalidAddressException extends RuntimeException {
    public InvalidAddressException() {
        super(com.arka.user_service.domain.util.ExceptionMessages.INVALID_ADDRESS_FORMAT);
    }
}

