package com.arka.user_service.global;

import com.arka.user_service.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, ServerWebExchange exchange) {
        return buildErrorResponseMono(ex, HttpStatus.NOT_FOUND, exchange, "USER_NOT_FOUND");
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public Mono<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, ServerWebExchange exchange) {
        return buildErrorResponseMono(ex, HttpStatus.CONFLICT, exchange, "USERNAME_ALREADY_EXISTS");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public Mono<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, ServerWebExchange exchange) {
        return buildErrorResponseMono(ex, HttpStatus.CONFLICT, exchange, "EMAIL_ALREADY_EXISTS");
    }

    @ExceptionHandler(InvalidEmailException.class)
    public Mono<ErrorResponse> handleInvalidEmailException(InvalidEmailException ex, ServerWebExchange exchange) {
        return buildErrorResponseMono(ex, HttpStatus.BAD_REQUEST, exchange, "INVALID_EMAIL_FORMAT");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public Mono<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex, ServerWebExchange exchange) {
        return buildErrorResponseMono(ex, HttpStatus.BAD_REQUEST, exchange, "INVALID_PASSWORD");
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public Mono<ErrorResponse> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException ex, ServerWebExchange exchange) {
        return buildErrorResponseMono(ex, HttpStatus.BAD_REQUEST, exchange, "PASSWORDS_DO_NOT_MATCH");
    }

    @ExceptionHandler(ClientNotAuthorizedException.class)
    public Mono<ErrorResponse> handleClientNotAuthorizedException(ClientNotAuthorizedException ex, ServerWebExchange exchange) {
        return buildErrorResponseMono(ex, HttpStatus.FORBIDDEN, exchange, "CLIENT_NOT_AUTHORIZED");
    }

    private Mono<ErrorResponse> buildErrorResponseMono(Exception ex, HttpStatus status, ServerWebExchange exchange, String errorCode) {
        exchange.getResponse().setStatusCode(status);
        return Mono.just(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(exchange.getRequest().getPath().value())
                        .errorCode(errorCode)
                        .build()
        );
    }
}
