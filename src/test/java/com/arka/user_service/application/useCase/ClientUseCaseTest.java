package com.arka.user_service.application.useCase;

import com.arka.user_service.application.ports.output.IClientOutPort;
import com.arka.user_service.domain.exception.InvalidAddressException;
import com.arka.user_service.domain.exception.InvalidFullNameException;
import com.arka.user_service.domain.exception.InvalidPhoneException;
import com.arka.user_service.domain.model.Client;
import com.arka.user_service.domain.service.AddressValidationService;
import com.arka.user_service.domain.service.FullNameValidationService;
import com.arka.user_service.domain.service.PhoneValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

class ClientUseCaseTest {
    @Mock
    private IClientOutPort clientOutPort;
    @Mock
    private FullNameValidationService fullNameValidationService;
    @Mock
    private AddressValidationService addressValidationService;
    @Mock
    private PhoneValidationService phoneValidationService;

    @InjectMocks
    private ClientUseCase clientUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientUseCase = new ClientUseCase(clientOutPort, fullNameValidationService, addressValidationService, phoneValidationService);
    }

    @Test
    void completeClientInfo_success() {
        UUID clientId = UUID.randomUUID();
        String fullName = "Cliente Test";
        String address = "Calle 123";
        String phone = "123456789";
        Client client = Client.builder()
                .id(clientId)
                .fullName(fullName)
                .address(address)
                .phone(phone)
                .loyaltyPoints(0)
                .build();

        when(fullNameValidationService.isValid(fullName)).thenReturn(true);
        when(addressValidationService.isValid(address)).thenReturn(true);
        when(phoneValidationService.isValid(phone)).thenReturn(true);
        when(clientOutPort.saveOrUpdateClientInfo(clientId, fullName, address, phone, 0)).thenReturn(Mono.just(client));

        StepVerifier.create(clientUseCase.completeClientInfo(clientId, fullName, address, phone))
                .expectNextMatches(c -> c.getId().equals(clientId) && c.getFullName().equals(fullName))
                .verifyComplete();
    }

    @Test
    void completeClientInfo_invalidFullName() {
        UUID clientId = UUID.randomUUID();
        String fullName = "X"; // Inválido
        String address = "Calle 123";
        String phone = "123456789";

        when(fullNameValidationService.isValid(fullName)).thenReturn(false);

        StepVerifier.create(clientUseCase.completeClientInfo(clientId, fullName, address, phone))
                .expectError(InvalidFullNameException.class)
                .verify();
    }

    @Test
    void completeClientInfo_invalidAddress() {
        UUID clientId = UUID.randomUUID();
        String fullName = "Cliente Test";
        String address = "X"; // Inválido
        String phone = "123456789";

        when(fullNameValidationService.isValid(fullName)).thenReturn(true);
        when(addressValidationService.isValid(address)).thenReturn(false);

        StepVerifier.create(clientUseCase.completeClientInfo(clientId, fullName, address, phone))
                .expectError(InvalidAddressException.class)
                .verify();
    }

    @Test
    void completeClientInfo_invalidPhone() {
        UUID clientId = UUID.randomUUID();
        String fullName = "Cliente Test";
        String address = "Calle 123";
        String phone = "abc"; // Inválido

        when(fullNameValidationService.isValid(fullName)).thenReturn(true);
        when(addressValidationService.isValid(address)).thenReturn(true);
        when(phoneValidationService.isValid(phone)).thenReturn(false);

        StepVerifier.create(clientUseCase.completeClientInfo(clientId, fullName, address, phone))
                .expectError(InvalidPhoneException.class)
                .verify();
    }
}
