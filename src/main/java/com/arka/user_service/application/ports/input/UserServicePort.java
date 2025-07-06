package com.arka.user_service.application.ports.input;

import com.arka.user_service.domain.model.User;
import com.arka.user_service.domain.model.LoginDomainResult;
import reactor.core.publisher.Mono;

public interface UserServicePort {

    Mono<LoginDomainResult> signIn(String username, String password);
    Mono<User> register(String username, String email, String password, String confirmPassword);

}
