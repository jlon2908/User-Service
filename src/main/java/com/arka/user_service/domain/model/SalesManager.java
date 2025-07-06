package com.arka.user_service.domain.model;

import lombok.*;
import java.util.UUID;
import java.time.LocalDate;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesManager {
    private UUID id;
    private String fullName;
    private String region;
    private Boolean goalAchieved;
    private LocalDate hireDate;
}

