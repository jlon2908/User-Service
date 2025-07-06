package com.arka.user_service.infraestructure.driven.db.repository;

import com.arka.user_service.infraestructure.driven.db.entity.InventoryManagerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface InventoryManagerRepository extends ReactiveCrudRepository<InventoryManagerEntity, UUID> {
    Mono<InventoryManagerEntity> findById(UUID id);
}

