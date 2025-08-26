package com.kawasaki.service.auth_service.controller;

import com.kawasaki.service.auth_service.dto.CreateProviderRequestDTO;
import com.kawasaki.service.auth_service.dto.CreateUserRequestDTO;
import com.kawasaki.service.auth_service.dto.ProviderRegisterRequestDTO;
import com.kawasaki.service.auth_service.dto.UserRegisterRequestDTO;
import com.kawasaki.service.auth_service.service.RegisterService;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/register")
public class RegisterController {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    RegisterService registerService;

    @PostMapping("/user")
    public ApiResponse<?> userRegister(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        // encrypt password
        String password_hash = encoder.encode(userRegisterRequestDTO.getPassword());

        CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO();
        BeanUtils.copyProperties(userRegisterRequestDTO, createUserRequestDTO);
        createUserRequestDTO.setPasswordHash(password_hash);

        String token = registerService.userRegister(createUserRequestDTO);
        return ApiResponse.success(token);
    }

    @PostMapping("/provider")
    public ApiResponse<?> providerRegister(@RequestBody ProviderRegisterRequestDTO providerRegisterRequestDTO) {
        // encrypt password
        String password_hash = encoder.encode(providerRegisterRequestDTO.getPassword());

        CreateProviderRequestDTO createProviderRequestDTO = new CreateProviderRequestDTO();
        BeanUtils.copyProperties(providerRegisterRequestDTO, createProviderRequestDTO);
        createProviderRequestDTO.setPasswordHash(password_hash);

        // call service and get token
        String token = registerService.providerRegister(createProviderRequestDTO);
        return ApiResponse.success(token);
    }
}
