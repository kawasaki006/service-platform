package com.kawasaki.service.auth_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawasaki.service.auth_service.DTO.LoginRequestDTO;
import com.kawasaki.service.auth_service.DTO.ProviderDTO;
import com.kawasaki.service.auth_service.DTO.UserDTO;
import com.kawasaki.service.auth_service.feign.ProviderFeignService;
import com.kawasaki.service.auth_service.feign.UserFeignService;
import com.kawasaki.service.auth_service.service.LoginService;
import com.kawasaki.service.common.utils.JWTUtils;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserFeignService userFeignService;

    @Autowired
    ProviderFeignService providerFeignService;

    @Override
    public String userLogin(LoginRequestDTO loginRequestDTO) {
        UserDTO userDTO = userFeignService.findUserByEmail(loginRequestDTO.getEmail()).getData();
        System.out.println(encoder.encode(userDTO.getPasswordHash()));

        if (!encoder.matches(loginRequestDTO.getPassword(), userDTO.getPasswordHash())) {
            throw new BizException(BizExceptionCodeEnum.INVALID_EMAIL_PASSWORD);
        }

        Map<String, String> payload = mapper.convertValue(userDTO, Map.class);
        payload.remove("passwordHash");
        payload.put("role", "user");
        String token = JWTUtils.generateToken(userDTO.getEmail(), payload);

        return token;
    }

    @Override
    public String providerLogin(String email, String password) {
        // get user instance with this email from db
        ProviderDTO providerDTO = providerFeignService.findProviderByEmail(email).getData();
        // password match
        System.out.println(encoder.encode(providerDTO.getPasswordHash()));

        if (!encoder.matches(password, providerDTO.getPasswordHash())) {
            throw new BizException(BizExceptionCodeEnum.INVALID_EMAIL_PASSWORD);
        }
        // generate jwt
        Map<String, String> payload = mapper.convertValue(providerDTO, Map.class);
        payload.remove("passwordHash");
        payload.put("role", "provider");
        String token = JWTUtils.generateToken(providerDTO.getEmail(), payload);

        return token;
    }
}
