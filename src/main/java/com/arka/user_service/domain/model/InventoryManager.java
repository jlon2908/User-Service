package com.arka.user_service.domain.model;

import lombok.*;
import java.util.UUID;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryManager {
    private UUID id;
    private String fullName;
    private String assignedWarehouseCode;
    private Integer pendingAudits;
    private LocalDate hireDate;
}

