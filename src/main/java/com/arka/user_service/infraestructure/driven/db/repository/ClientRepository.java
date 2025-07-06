package com.arka.user_service.infraestructure.driven.db.repository;

import com.arka.user_service.infraestructure.driven.db.entity.ClientEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, UUID> {
    @Query("SELECT EXISTS (SELECT 1 FROM clients WHERE id = :id)")
    Mono<Boolean> existsClientById(UUID id);

    @Query("UPDATE clients SET full_name = :fullName, address = :address, phone = :phone, loyalty_points = :loyaltyPoints WHERE id = :id")
    Mono<Void> updateClient(UUID id, String fullName, String address, String phone, Integer loyaltyPoints);

    @Query("INSERT INTO clients (id, full_name, address, phone, loyalty_points) VALUES (:id, :fullName, :address, :phone, :loyaltyPoints)")
    Mono<Void> insertClient(UUID id, String fullName, String address, String phone, Integer loyaltyPoints);

    @Query("SELECT * FROM clients WHERE id = :id")
    Mono<ClientEntity> findClientById(UUID id);
}

