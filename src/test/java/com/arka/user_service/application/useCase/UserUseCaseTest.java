package com.arka.user_service.application.useCase;

import com.arka.user_service.application.ports.output.IUserOutPort;
import com.arka.user_service.domain.exception.*;
import com.arka.user_service.domain.model.Role;
import com.arka.user_service.domain.model.User;
import com.arka.user_service.domain.service.EmailValidationService;
import com.arka.user_service.domain.service.PasswordValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

class UserUseCaseTest {
    @Mock
    private IUserOutPort iUserOutPort;
    @Mock
    private EmailValidationService emailValidationService;
    @Mock
    private PasswordValidationService passwordValidationService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(iUserOutPort, emailValidationService, passwordValidationService, passwordEncoder);
    }

    @Test
    void register_success() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "Password1!";
        String confirmPassword = "Password1!";
        Role clientRole = Role.builder().id(1).name("CLIENT").build();
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .email(email)
                .password("hashed")
                .enabled(true)
                .role(clientRole)
                .build();

        when(emailValidationService.isValid(email)).thenReturn(true);
        when(passwordValidationService.isValid(password)).thenReturn(true);
        when(iUserOutPort.findByUsername(username)).thenReturn(Mono.empty());
        when(iUserOutPort.findByEmail(email)).thenReturn(Mono.empty());
        when(passwordEncoder.encode(password)).thenReturn("hashed");
        when(iUserOutPort.save(ArgumentMatchers.any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.register(username, email, password, confirmPassword))
                .expectNextMatches(u -> u.getUsername().equals(username) && u.getEmail().equals(email))
                .verifyComplete();
    }

    @Test
    void signIn_success_clientRole() {
        String username = "testuser";
        String password = "Password1!";
        String hashedPassword = "hashed";
        Role clientRole = Role.builder().id(1).name("CLIENT").build();
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .email("test@example.com")
                .password(hashedPassword)
                .enabled(true)
                .role(clientRole)
                .build();
        com.arka.user_service.domain.model.Client clientDomain = com.arka.user_service.domain.model.Client.builder()
                .id(UUID.randomUUID())
                .fullName("Cliente Test")
                .phone("123456789")
                .address("Calle 123")
                .loyaltyPoints(0)
                .build();

        when(iUserOutPort.findByUsername(username)).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);
        when(iUserOutPort.findClientByUserId(user.getId())).thenReturn(Mono.just(clientDomain));

        StepVerifier.create(userUseCase.signIn(username, password))
                .expectNextMatches(result ->
                        result.getUser().getUsername().equals(username) &&
                        result.getDomainUser() instanceof com.arka.user_service.domain.model.Client &&
                        ((com.arka.user_service.domain.model.Client) result.getDomainUser()).getFullName().equals("Cliente Test")
                )
                .verifyComplete();
    }
}
