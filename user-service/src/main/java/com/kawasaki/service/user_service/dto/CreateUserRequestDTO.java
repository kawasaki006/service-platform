package com.kawasaki.service.user_service.dto;

import lombok.Data;

@Data
public class CreateUserRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String passwordHash;
}
