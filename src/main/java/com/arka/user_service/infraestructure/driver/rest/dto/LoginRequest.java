package com.arka.user_service.infraestructure.driver.rest.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
