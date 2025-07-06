package com.arka.user_service.infraestructure.driven.db.adapter;

import com.arka.user_service.application.ports.output.IClientOutPort;
import com.arka.user_service.domain.model.Client;
import com.arka.user_service.infraestructure.driven.db.entity.ClientEntity;
import com.arka.user_service.infraestructure.driven.db.mapper.ClientPersistenceMapper;
import com.arka.user_service.infraestructure.driven.db.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientPersistenceAdapter implements IClientOutPort {
    private final ClientRepository clientRepository;


    @Override
    public Mono<Client> saveOrUpdateClientInfo(UUID clientId, String fullName, String address, String phone, Integer loyaltyPoints) {
        return clientRepository.existsClientById(clientId)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return clientRepository.updateClient(clientId, fullName, address, phone, loyaltyPoints)
                                .then(clientRepository.findClientById(clientId));
                    } else {
                        return clientRepository.insertClient(clientId, fullName, address, phone, loyaltyPoints)
                                .then(clientRepository.findClientById(clientId));
                    }
                })
                .map(ClientPersistenceMapper::toDomain);
    }    }

