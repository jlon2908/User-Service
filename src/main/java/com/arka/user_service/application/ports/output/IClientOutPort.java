package com.arka.user_service.application.ports.output;

import com.arka.user_service.domain.model.Client;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface IClientOutPort {
    Mono<Client> saveOrUpdateClientInfo(UUID clientId, String fullName, String address, String phone, Integer loyaltyPoints);
}
