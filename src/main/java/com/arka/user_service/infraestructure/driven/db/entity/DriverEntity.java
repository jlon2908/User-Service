package com.arka.user_service.infraestructure.driven.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.util.UUID;
import java.time.LocalDate;

@Table("drivers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverEntity {
    @Id
    private UUID id;
    @Column("full_name")
    private String fullName;
    @Column("license_number")
    private String licenseNumber;
    @Column("vehicle_plate")
    private String vehiclePlate;
    private Boolean available;
    @Column("hire_date")
    private LocalDate hireDate;
}

