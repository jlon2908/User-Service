package com.arka.user_service.infraestructure.driven.db.repository;

import com.arka.user_service.infraestructure.driven.db.entity.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<RoleEntity, Integer> {
}

