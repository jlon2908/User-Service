package com.arka.user_service.infraestructure.driven.db.repository;

import com.arka.user_service.infraestructure.driven.db.entity.DriverEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface DriverRepository extends ReactiveCrudRepository<DriverEntity, UUID> {
    Mono<DriverEntity> findById(UUID id);
}

