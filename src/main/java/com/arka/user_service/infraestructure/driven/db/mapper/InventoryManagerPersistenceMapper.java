package com.arka.user_service.infraestructure.driven.db.mapper;

import com.arka.user_service.domain.model.InventoryManager;
import com.arka.user_service.infraestructure.driven.db.entity.InventoryManagerEntity;

public class InventoryManagerPersistenceMapper {
    public static InventoryManager toDomain(InventoryManagerEntity entity) {
        if (entity == null) return null;
        return InventoryManager.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .assignedWarehouseCode(entity.getAssignedWarehouseCode())
                .pendingAudits(entity.getPendingAudits())
                .hireDate(entity.getHireDate())
                .build();
    }
}

