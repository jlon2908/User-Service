package com.arka.user_service.infraestructure.driven.db.mapper;

import com.arka.user_service.domain.model.User;
import com.arka.user_service.domain.model.Role;
import com.arka.user_service.infraestructure.driven.db.entity.UserEntity;
import com.arka.user_service.infraestructure.driven.db.entity.RoleEntity;

public class UserPersistenceMapper {
    public static User toDomain(UserEntity entity, Role role) {
        if (entity == null) return null;
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .enabled(entity.getEnabled())
                .role(role)
                .build();
    }

    public static UserEntity toEntity(User user) {
        if (user == null) return null;
        UserEntity.UserEntityBuilder builder = UserEntity.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.getEnabled())
                .roleId(user.getRole() != null ? user.getRole().getId() : null);
        if (user.getId() != null) {
            builder.id(user.getId());
        }
        return builder.build();
    }

    public static Role toDomain(RoleEntity entity) {
        if (entity == null) return null;
        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static RoleEntity toEntity(Role role) {
        if (role == null) return null;
        return RoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
