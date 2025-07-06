package com.arka.user_service.infraestructure.driver.rest.controller;

import com.arka.user_service.application.ports.input.UserServicePort;
import com.arka.user_service.infraestructure.driver.rest.dto.MessageResponseDto;
import com.arka.user_service.infraestructure.driver.rest.dto.RegisterUserRequest;
import com.arka.user_service.infraestructure.driver.rest.dto.LoginRequest;
import com.arka.user_service.infraestructure.driver.rest.dto.LoginResponseDto;
import com.arka.user_service.infraestructure.driven.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServicePort userServicePort;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public Mono<ResponseEntity<MessageResponseDto>> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        return userServicePort.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword()
        ).thenReturn(ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponseDto("El usuario ha sido creado satisfactoriamente")));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponseDto>> login(@RequestBody LoginRequest request) {
        return userServicePort.signIn(request.getUsername(), request.getPassword())
                .map(result -> {
                    String token = jwtUtil.generateToken(result.getUser(), result.getDomainUser());
                    return ResponseEntity.ok(new LoginResponseDto(token));
                });
    }
}
