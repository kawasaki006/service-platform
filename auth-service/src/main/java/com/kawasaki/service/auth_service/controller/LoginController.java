package com.kawasaki.service.auth_service.controller;

import com.kawasaki.service.auth_service.dto.LoginRequestDTO;
import com.kawasaki.service.auth_service.security.config.UserDetailsImpl;
import com.kawasaki.service.auth_service.service.LoginService;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.common.utils.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @GetMapping("/user")
//    public ApiResponse<?> userLogin(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
//        String token = loginService.userLogin(loginRequestDTO);
//        return ApiResponse.success(token);
//    }

    @GetMapping("/user")
    public ApiResponse<?> userLogin(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authentication)) {
            // TODO: Some other messages
            throw new BizException(500, "Fail to log in");
        }

        UserDetailsImpl loggedInUser = (UserDetailsImpl) authentication.getPrincipal();

        // store in context holder TODO: principal of this authentication is different from the one in jwtfilter
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get jwt
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", loggedInUser.getId().toString());
        List<String> roles = loggedInUser.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        payload.put("roles", roles);
        String jwt = JWTUtils.generateToken(loggedInUser.getUsername(), payload);

        System.out.println("roles: " + roles);

        // return
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ApiResponse.success(map);
    }

    @GetMapping("/provider")
    public ApiResponse<?> providerLogin(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        String token = loginService.providerLogin(email, password);
        return ApiResponse.success(token);
    }
}
