package com.arka.user_service.infraestructure.driven.db.mapper;

import com.arka.user_service.domain.model.Client;
import com.arka.user_service.infraestructure.driven.db.entity.ClientEntity;

public class ClientPersistenceMapper {
    public static Client toDomain(ClientEntity entity) {
        if (entity == null) return null;
        return Client.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .loyaltyPoints(entity.getLoyaltyPoints())
                .build();
    }
}

