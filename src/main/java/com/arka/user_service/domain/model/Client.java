package com.arka.user_service.domain.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    private UUID id;
    private String fullName;
    private String phone;
    private String address;
    private Integer loyaltyPoints;
}

