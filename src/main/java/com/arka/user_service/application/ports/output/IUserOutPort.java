package com.arka.user_service.application.ports.output;


import com.arka.user_service.domain.model.User;
import com.arka.user_service.domain.model.Client;
import com.arka.user_service.domain.model.Driver;
import com.arka.user_service.domain.model.InventoryManager;
import com.arka.user_service.domain.model.SalesManager;
import com.arka.user_service.domain.model.Admin;
import reactor.core.publisher.Mono;

public interface IUserOutPort  {
    Mono<User> findByUsername(String username);
    Mono<User> findByEmail(String email);
    Mono<User> save(User user);

    Mono<Client> findClientByUserId(java.util.UUID userId);
    Mono<Driver> findDriverByUserId(java.util.UUID userId);
    Mono<InventoryManager> findInventoryManagerByUserId(java.util.UUID userId);
    Mono<SalesManager> findSalesManagerByUserId(java.util.UUID userId);
    Mono<Admin> findAdminByUserId(java.util.UUID userId);
}