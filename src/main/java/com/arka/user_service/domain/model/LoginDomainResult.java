package com.arka.user_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginDomainResult {
    private final User user;
    private final Object domainUser;
}

