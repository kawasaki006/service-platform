package com.kawasaki.service.auth_service.service;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginService {
    public String providerLogin(String email, String password);
}
