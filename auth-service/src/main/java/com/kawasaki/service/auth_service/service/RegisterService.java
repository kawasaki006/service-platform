package com.kawasaki.service.auth_service.service;

import com.kawasaki.service.auth_service.dto.CreateProviderRequestDTO;

public interface RegisterService {
    public String providerRegister(CreateProviderRequestDTO createProviderRequestDTO);
}
