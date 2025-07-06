package com.arka.user_service.infraestructure.driven.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.util.UUID;
import java.time.LocalDate;

@Table("inventory_managers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryManagerEntity {
    @Id
    private UUID id;
    @Column("full_name")
    private String fullName;
    @Column("assigned_warehouse_code")
    private String assignedWarehouseCode;
    @Column("pending_audits")
    private Integer pendingAudits;
    @Column("hire_date")
    private LocalDate hireDate;
}

