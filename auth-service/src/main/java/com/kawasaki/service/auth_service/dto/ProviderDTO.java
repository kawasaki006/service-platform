package com.kawasaki.service.auth_service.dto;

import lombok.Data;

@Data
public class ProviderDTO {
    private Long id;
    private String name;
    private String email;
    private String passwordHash;
}
