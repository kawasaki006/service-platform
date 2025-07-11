package com.kawasaki.service.provider_service.DTO;

import lombok.Data;

@Data
public class ProviderDTO {
    private Long id;
    private String name;
    private String email;
    private String passwordHash;
}
