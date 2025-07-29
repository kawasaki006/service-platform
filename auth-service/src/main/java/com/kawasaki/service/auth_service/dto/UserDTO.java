package com.kawasaki.service.auth_service.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String passwordHash;

    private List<GrantedAuthority>  authorities;
}