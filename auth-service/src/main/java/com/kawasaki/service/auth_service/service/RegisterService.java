package com.kawasaki.service.auth_service.service;

import com.kawasaki.service.auth_service.DTO.CreateProviderRequestDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterService {
    public String providerRegister(CreateProviderRequestDTO createProviderRequestDTO);
}
