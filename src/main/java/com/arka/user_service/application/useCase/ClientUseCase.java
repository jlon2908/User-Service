package com.arka.user_service.application.useCase;

import com.arka.user_service.application.ports.input.IClientServicePort;
import com.arka.user_service.application.ports.output.IClientOutPort;
import com.arka.user_service.domain.exception.InvalidAddressException;
import com.arka.user_service.domain.exception.InvalidFullNameException;
import com.arka.user_service.domain.exception.InvalidPhoneException;
import com.arka.user_service.domain.model.Client;
import com.arka.user_service.domain.service.AddressValidationService;
import com.arka.user_service.domain.service.FullNameValidationService;
import com.arka.user_service.domain.service.PhoneValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {
    private final IClientOutPort clientOutPort;
    private final FullNameValidationService fullNameValidationService;
    private final AddressValidationService addressValidationService;
    private final PhoneValidationService phoneValidationService;

    @Override
    public Mono<Client> completeClientInfo(UUID clientId, String fullName, String address, String phone) {
        if (!fullNameValidationService.isValid(fullName)) {
            return Mono.error(new InvalidFullNameException());
        }
        if (!addressValidationService.isValid(address)) {
            return Mono.error(new InvalidAddressException());
        }
        if (!phoneValidationService.isValid(phone)) {
            return Mono.error(new InvalidPhoneException());
        }
        // loyaltyPoints siempre en 0 al completar info
        return clientOutPort.saveOrUpdateClientInfo(clientId, fullName, address, phone, 0);
    }
}
