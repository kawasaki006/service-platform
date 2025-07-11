package com.kawasaki.service.auth_service.controller;

import com.kawasaki.service.auth_service.DTO.CreateProviderRequestDTO;
import com.kawasaki.service.auth_service.DTO.RegisterRequestDTO;
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

    @PostMapping("/provider")
    public ApiResponse<?> providerRegister(@RequestBody RegisterRequestDTO registerRequestDTO) {
        // encrypt password
        String password_hash = encoder.encode(registerRequestDTO.getPassword());

        CreateProviderRequestDTO createProviderRequestDTO = new CreateProviderRequestDTO();
        BeanUtils.copyProperties(registerRequestDTO, createProviderRequestDTO);
        createProviderRequestDTO.setPasswordHash(password_hash);

        // call service and get token
        String token = registerService.providerRegister(createProviderRequestDTO);
        return ApiResponse.success(token);
    }
}
