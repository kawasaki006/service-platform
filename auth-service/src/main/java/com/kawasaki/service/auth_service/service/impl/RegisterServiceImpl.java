package com.kawasaki.service.auth_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawasaki.service.auth_service.dto.CreateProviderRequestDTO;
import com.kawasaki.service.auth_service.dto.CreateUserRequestDTO;
import com.kawasaki.service.auth_service.dto.ProviderDTO;
import com.kawasaki.service.auth_service.dto.UserDTO;
import com.kawasaki.service.auth_service.feign.ProviderFeignService;
import com.kawasaki.service.auth_service.feign.UserFeignService;
import com.kawasaki.service.auth_service.service.RegisterService;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.common.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private ProviderFeignService providerFeignService;

    @Override
    public String userRegister(CreateUserRequestDTO createUserRequestDTO) {
        // check if email exists in provider db
        ProviderDTO providerDTO = providerFeignService.findProviderByEmail(createUserRequestDTO.getEmail()).getData();
        if (!Objects.isNull(providerDTO)) {
            throw new BizException(BizExceptionCodeEnum.EMAIL_EXISTS);
        }

        // feign call
        UserDTO userDTO = userFeignService.createUser(createUserRequestDTO).getData();
        Map<String, Object> payload = mapper.convertValue(userDTO, Map.class);
        payload.remove("passwordHash");
        payload.put("role", "user");

        return JWTUtils.generateToken(userDTO.getEmail(), payload);
    }

    @Override
    public String providerRegister(CreateProviderRequestDTO createProviderRequestDTO) {
        // check if email exists in user db
        UserDTO userDTO = userFeignService.findUserByEmail(createProviderRequestDTO.getEmail()).getData();
        if (!Objects.isNull(userDTO)) {
            throw new BizException(BizExceptionCodeEnum.EMAIL_EXISTS);
        }

        // feign call: post provider
        ProviderDTO providerDTO = providerFeignService.createProvider(createProviderRequestDTO).getData();
        Map<String, Object> payload = mapper.convertValue(providerDTO, Map.class);
        payload.remove("passwordHash");
        payload.put("role", "provider");

        return JWTUtils.generateToken(providerDTO.getEmail(), payload);
    }
}
