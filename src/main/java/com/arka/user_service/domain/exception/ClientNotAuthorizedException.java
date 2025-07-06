package com.arka.user_service.domain.exception;

public class ClientNotAuthorizedException extends RuntimeException {
    public ClientNotAuthorizedException(String message) {
        super(message);
    }
}
