package com.arka.user_service.infraestructure.driver.rest.controller;

import com.arka.user_service.application.ports.input.IClientServicePort;
import com.arka.user_service.domain.model.Client;
import com.arka.user_service.infraestructure.driver.rest.dto.CompleteClientInfoRequest;
import com.arka.user_service.infraestructure.driven.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientServicePort clientServicePort;
    private final JwtUtil jwtUtil;


    @PutMapping("/profile")
    public Mono<ResponseEntity<Client>> completeProfile(@Valid @RequestBody CompleteClientInfoRequest request,
                                                       ServerWebExchange exchange) {


        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        String token = authHeader.substring(7);
        String userIdStr = jwtUtil.getAllClaimsFromToken(token).get("userId", String.class);
        UUID userId = UUID.fromString(userIdStr);
        return clientServicePort.completeClientInfo(userId, request.getFullName(), request.getAddress(), request.getPhone())
                .map(client -> ResponseEntity.ok(client));
    }
}

