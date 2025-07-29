package com.kawasaki.service.auth_service.service;

import com.kawasaki.service.auth_service.dto.LoginRequestDTO;

public interface LoginService {
    public String userLogin(LoginRequestDTO loginRequestDTO);
    public String providerLogin(String email, String password);
}
