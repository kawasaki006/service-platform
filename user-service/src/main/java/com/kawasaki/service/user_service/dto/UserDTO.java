package com.kawasaki.service.user_service.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String passwordHash;
}
