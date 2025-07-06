package com.arka.user_service.domain.model;

import lombok.*;
import java.util.UUID;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    private UUID id;
    private String fullName;
    private String permissions;
    private LocalDate hireDate;
}

