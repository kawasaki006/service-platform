package com.kawasaki.service.auth_service.controller;

import com.kawasaki.service.auth_service.DTO.LoginRequestDTO;
import com.kawasaki.service.auth_service.service.LoginService;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/provider")
    public ApiResponse<?> providerLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        String token = loginService.providerLogin(email, password);
        return ApiResponse.success(token);
    }
}
