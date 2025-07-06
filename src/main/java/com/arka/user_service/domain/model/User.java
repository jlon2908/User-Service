package com.arka.user_service.domain.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  User {
    private UUID id;
    private String email;
    private String username;
    private String password;
    private Boolean enabled;
    private Role role;
}

