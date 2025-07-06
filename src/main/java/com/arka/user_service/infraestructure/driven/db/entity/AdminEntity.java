package com.arka.user_service.infraestructure.driven.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.util.UUID;
import java.time.LocalDate;

@Table("admins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminEntity {
    @Id
    private UUID id;
    @Column("full_name")
    private String fullName;
    private String permissions;
    @Column("hire_date")
    private LocalDate hireDate;
}

