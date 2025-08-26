package com.kawasaki.service.auth_service.service;

import com.kawasaki.service.auth_service.dto.CreateProviderRequestDTO;
import com.kawasaki.service.auth_service.dto.CreateUserRequestDTO;

public interface RegisterService {
    String providerRegister(CreateProviderRequestDTO createProviderRequestDTO);

    String userRegister(CreateUserRequestDTO createUserRequestDTO);
}
