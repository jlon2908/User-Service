package com.arka.user_service.infraestructure.driven.db.repository;

import com.arka.user_service.infraestructure.driven.db.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
    Mono<UserEntity> findByUsername(String username);
    Mono<UserEntity> findByEmail(String email);

    @Query("INSERT INTO users (id, email, username, password, enabled, role_id) VALUES ($1, $2, $3, $4, $5, $6) RETURNING *")
    Mono<UserEntity> insertUser(UUID id, String email, String username, String password, Boolean enabled, Integer roleId);
}
