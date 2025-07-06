package com.arka.user_service.application.useCase;

import com.arka.user_service.application.ports.input.UserServicePort;
import com.arka.user_service.application.ports.output.IUserOutPort;
import com.arka.user_service.domain.exception.*;
import com.arka.user_service.domain.model.Role;
import com.arka.user_service.domain.model.User;
import com.arka.user_service.domain.model.LoginDomainResult;
import com.arka.user_service.domain.service.EmailValidationService;
import com.arka.user_service.domain.service.PasswordValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserUseCase implements UserServicePort {
    private final IUserOutPort iUserOutPort;
    private final EmailValidationService emailValidationService;
    private final PasswordValidationService passwordValidationService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Mono<User> register(String username, String email, String password, String confirmPassword) {
        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            return Mono.error(new PasswordsDoNotMatchException());
        }
        // Validar formato de email
        if (!emailValidationService.isValid(email)) {
            return Mono.error(new InvalidEmailException());
        }
        // Validar formato de password
        if (!passwordValidationService.isValid(password)) {
            return Mono.error(new InvalidPasswordException());
        }
        // Validar que el username no exista
        return iUserOutPort.findByUsername(username)
                .flatMap(existingUser -> Mono.<User>error(new UsernameAlreadyExistsException()))
                .switchIfEmpty(
                        iUserOutPort.findByEmail(email)
                                .flatMap(existingUser -> Mono.<User>error(new EmailAlreadyExistsException()))
                                .switchIfEmpty(
                                        Mono.defer(() -> {
                                            // Asignar rol CLIENT automáticamente
                                            Role clientRole = Role.builder().id(1).name("CLIENT").build();
                                            String hashedPassword = passwordEncoder.encode(password);
                                            User user = User.builder()
                                                    .id(java.util.UUID.randomUUID())
                                                    .username(username)
                                                    .email(email)
                                                    .password(hashedPassword)
                                                    .enabled(true)
                                                    .role(clientRole)
                                                    .build();
                                            return iUserOutPort.save(user);
                                        })
                                )
                );
    }

    @Override
    public Mono<LoginDomainResult> signIn(String username, String password) {
        return iUserOutPort.findByUsername(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(user -> {
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.error(new UserNotFoundException());
                    }
                    String role = user.getRole().getName();
                    Mono<?> domainMono;
                    switch (role) {
                        case "CLIENT":
                            domainMono = iUserOutPort.findClientByUserId(user.getId());
                            break;
                        case "DRIVER":
                            domainMono = iUserOutPort.findDriverByUserId(user.getId());
                            break;
                        case "INVENTORY_MANAGER":
                            domainMono = iUserOutPort.findInventoryManagerByUserId(user.getId());
                            break;
                        case "SALES_MANAGER":
                            domainMono = iUserOutPort.findSalesManagerByUserId(user.getId());
                            break;
                        case "ADMIN":
                            domainMono = iUserOutPort.findAdminByUserId(user.getId());
                            break;
                        default:
                            domainMono = Mono.empty();
                    }
                    return domainMono
                        .map(domainUser -> LoginDomainResult.builder()
                                .user(user)
                                .domainUser(domainUser)
                                .build())
                        .defaultIfEmpty(LoginDomainResult.builder().user(user).domainUser(null).build());
                });
    }
}
