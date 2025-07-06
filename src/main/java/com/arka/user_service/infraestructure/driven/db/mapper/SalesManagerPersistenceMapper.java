package com.arka.user_service.infraestructure.driven.db.mapper;

import com.arka.user_service.domain.model.SalesManager;
import com.arka.user_service.infraestructure.driven.db.entity.SalesManagerEntity;

public class SalesManagerPersistenceMapper {
    public static SalesManager toDomain(SalesManagerEntity entity) {
        if (entity == null) return null;
        return SalesManager.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .region(entity.getRegion())
                .goalAchieved(entity.getGoalAchieved())
                .hireDate(entity.getHireDate())
                .build();
    }
}

