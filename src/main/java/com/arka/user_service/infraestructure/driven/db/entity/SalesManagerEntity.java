package com.arka.user_service.infraestructure.driven.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.util.UUID;
import java.time.LocalDate;

@Table("sales_managers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesManagerEntity {
    @Id
    private UUID id;
    @Column("full_name")
    private String fullName;
    private String region;
    @Column("goal_achieved")
    private Boolean goalAchieved;
    @Column("hire_date")
    private LocalDate hireDate;
}

