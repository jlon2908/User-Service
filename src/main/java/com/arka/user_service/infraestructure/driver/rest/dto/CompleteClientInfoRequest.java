package com.arka.user_service.infraestructure.driver.rest.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CompleteClientInfoRequest {
    private String fullName;
    private String address;
    private String phone;
}

