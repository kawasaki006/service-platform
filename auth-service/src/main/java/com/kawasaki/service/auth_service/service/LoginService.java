package com.kawasaki.service.auth_service.service;

import com.kawasaki.service.auth_service.DTO.LoginRequestDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginService {
    public String userLogin(LoginRequestDTO loginRequestDTO);
    public String providerLogin(String email, String password);
}
