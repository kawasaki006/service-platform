package com.kawasaki.service.provider_service.DTO;

import lombok.Data;

@Data
public class CreateProviderRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String passwordHash;
    private String bio;
}
