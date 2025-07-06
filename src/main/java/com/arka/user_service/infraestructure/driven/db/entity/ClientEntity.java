package com.arka.user_service.infraestructure.driven.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.util.UUID;

@Table("clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientEntity {
    @Id
    private UUID id;
    @Column("full_name")
    private String fullName;
    private String phone;
    private String address;
    @Column("loyalty_points")
    private Integer loyaltyPoints;
}

