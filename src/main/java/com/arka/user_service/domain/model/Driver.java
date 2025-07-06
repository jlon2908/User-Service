package com.arka.user_service.domain.model;

import lombok.*;
import java.util.UUID;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {
    private UUID id;
    private String fullName;
    private String licenseNumber;
    private String vehiclePlate;
    private Boolean available;
    private LocalDate hireDate;
}

