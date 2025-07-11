package com.kawasaki.service.auth_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawasaki.service.auth_service.DTO.CreateProviderRequestDTO;
import com.kawasaki.service.auth_service.DTO.ProviderDTO;
import com.kawasaki.service.auth_service.feign.ProviderFeignService;
import com.kawasaki.service.auth_service.service.RegisterService;
import com.kawasaki.service.common.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    ProviderFeignService providerFeignService;

    @Override
    public String providerRegister(CreateProviderRequestDTO createProviderRequestDTO) {
        // feign call: post provider
        ProviderDTO providerDTO = providerFeignService.createProvider(createProviderRequestDTO).getData();
        // return
        Map<String, String> payload = mapper.convertValue(providerDTO, Map.class);
        payload.remove("passwordHash");
        String token = JWTUtils.generateToken(providerDTO.getEmail(), payload);

        return token;
    }
}
