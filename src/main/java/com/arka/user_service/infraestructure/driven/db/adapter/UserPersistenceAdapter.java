package com.arka.user_service.infraestructure.driven.db.adapter;

import com.arka.user_service.application.ports.output.IUserOutPort;
import com.arka.user_service.domain.model.*;
import com.arka.user_service.infraestructure.driven.db.entity.UserEntity;
import com.arka.user_service.infraestructure.driven.db.repository.UserRepository;
import com.arka.user_service.infraestructure.driven.db.repository.RoleRepository;
import com.arka.user_service.infraestructure.driven.db.repository.ClientRepository;
import com.arka.user_service.infraestructure.driven.db.repository.DriverRepository;
import com.arka.user_service.infraestructure.driven.db.repository.InventoryManagerRepository;
import com.arka.user_service.infraestructure.driven.db.repository.SalesManagerRepository;
import com.arka.user_service.infraestructure.driven.db.repository.AdminRepository;
import com.arka.user_service.infraestructure.driven.db.mapper.UserPersistenceMapper;
import com.arka.user_service.infraestructure.driven.db.mapper.ClientPersistenceMapper;
import com.arka.user_service.infraestructure.driven.db.mapper.DriverPersistenceMapper;
import com.arka.user_service.infraestructure.driven.db.mapper.InventoryManagerPersistenceMapper;
import com.arka.user_service.infraestructure.driven.db.mapper.SalesManagerPersistenceMapper;
import com.arka.user_service.infraestructure.driven.db.mapper.AdminPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter  implements IUserOutPort {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;
    private final DriverRepository driverRepository;
    private final InventoryManagerRepository inventoryManagerRepository;
    private final SalesManagerRepository salesManagerRepository;
    private final AdminRepository adminRepository;

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .flatMap(userEntity ->
                        roleRepository.findById(userEntity.getRoleId())
                                .map(UserPersistenceMapper::toDomain)
                                .map(role -> UserPersistenceMapper.toDomain(userEntity, role))
                );
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(userEntity ->
                        roleRepository.findById(userEntity.getRoleId())
                                .map(UserPersistenceMapper::toDomain)
                                .map(role -> UserPersistenceMapper.toDomain(userEntity, role))
                );
    }

    @Override
    public Mono<User> save(User user) {
        UserEntity entity = UserPersistenceMapper.toEntity(user);
        return userRepository.insertUser(
                entity.getId(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEnabled(),
                entity.getRoleId()
        ).flatMap(savedEntity ->
                roleRepository.findById(savedEntity.getRoleId())
                        .map(UserPersistenceMapper::toDomain)
                        .map(role -> UserPersistenceMapper.toDomain(savedEntity, role))
        );
    }

    @Override
    public Mono<Client> findClientByUserId(UUID userId) {
        return clientRepository.findById(userId)
                .map(ClientPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Driver> findDriverByUserId(UUID userId) {
        return driverRepository.findById(userId)
                .map(DriverPersistenceMapper::toDomain);
    }

    @Override
    public Mono<InventoryManager> findInventoryManagerByUserId(UUID userId) {
        return inventoryManagerRepository.findById(userId)
                .map(InventoryManagerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<SalesManager> findSalesManagerByUserId(UUID userId) {
        return salesManagerRepository.findById(userId)
                .map(SalesManagerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Admin> findAdminByUserId(UUID userId) {
        return adminRepository.findById(userId)
                .map(AdminPersistenceMapper::toDomain);
    }
}
