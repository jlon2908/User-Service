package com.arka.user_service.infraestructure.driven.db.mapper;

import com.arka.user_service.domain.model.Admin;
import com.arka.user_service.infraestructure.driven.db.entity.AdminEntity;

public class AdminPersistenceMapper {
    public static Admin toDomain(AdminEntity entity) {
        if (entity == null) return null;
        return Admin.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .permissions(entity.getPermissions())
                .hireDate(entity.getHireDate())
                .build();
    }
}

