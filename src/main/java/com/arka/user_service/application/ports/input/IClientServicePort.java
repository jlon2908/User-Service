package com.arka.user_service.application.ports.input;

import com.arka.user_service.domain.model.Client;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface IClientServicePort {
    Mono<Client> completeClientInfo(UUID clientId, String fullName, String address, String phone);
}
