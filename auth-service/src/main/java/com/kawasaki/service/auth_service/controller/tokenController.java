package com.kawasaki.service.auth_service.controller;

import com.kawasaki.service.common.dto.InternalCredentialRequestDTO;
import com.kawasaki.service.auth_service.service.InternalClientService;
import com.kawasaki.service.common.dto.InternalCredentialResponseDTO;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.common.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/token")
public class tokenController {
    @Autowired
    InternalClientService internalClientService;

    @PostMapping("/client-credentials")
    public ApiResponse<?> clientCredentials(@RequestBody InternalCredentialRequestDTO requestDTO) {
        if (!internalClientService.validateClient(requestDTO.getClientId(), requestDTO.getClientSecret())) {
            throw new BizException(HttpStatus.UNAUTHORIZED.value(), "Invalid internal client request");
        }

        if (!"client_credentials".equals(requestDTO.getGrantType())) {
            throw new BizException(HttpStatus.BAD_REQUEST.value(), "Unsupported grant type");
        }

        List<String> scopes = internalClientService.getClientScopes(requestDTO.getClientId());

        Map<String, Object> payload = new HashMap<>();
        payload.put("client_id", requestDTO.getClientId());
        payload.put("scopes", scopes);

        String token = JWTUtils.generateToken(requestDTO.getClientId(), payload, "internal");

        InternalCredentialResponseDTO responseDTO = new InternalCredentialResponseDTO();
        responseDTO.setAccessToken(token);
        responseDTO.setScope(String.join(" ", scopes));
//        data.put("token_type", "Bearer");
//        data.put("expires_in", 3600);

        return ApiResponse.success(responseDTO);
    }
}
