package com.arka.user_service.infraestructure.driven.db.mapper;

import com.arka.user_service.domain.model.Driver;
import com.arka.user_service.infraestructure.driven.db.entity.DriverEntity;

public class DriverPersistenceMapper {
    public static Driver toDomain(DriverEntity entity) {
        if (entity == null) return null;
        return Driver.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .licenseNumber(entity.getLicenseNumber())
                .vehiclePlate(entity.getVehiclePlate())
                .available(entity.getAvailable())
                .hireDate(entity.getHireDate())
                .build();
    }
}

