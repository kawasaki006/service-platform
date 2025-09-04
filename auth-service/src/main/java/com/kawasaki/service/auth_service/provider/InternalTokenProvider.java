package com.kawasaki.service.auth_service.provider;

import com.kawasaki.service.common.dto.InternalCredentialRequestDTO;
import com.kawasaki.service.common.dto.InternalCredentialResponseDTO;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.common.utils.JWTUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InternalTokenProvider {
    @Value("${auth.client.id}")
    private String clientId;

    @Value("${auth.client.secret}")
    private String clientSecret;

    @Value("${auth.client.url}")
    private String authServiceUrl;

    private String cachedToken;

    @Autowired
    private RestTemplate restTemplate;

    public String getInternalToken() {
        if (cachedToken != null) {
            try {
                JWTUtils.verifyToken(cachedToken);
                return cachedToken;
            } catch (JwtException | IllegalArgumentException | BizException e) {
                // invalid cached token
                cachedToken = null; // invalidate cache
            }
        }
        return  requestNewToken();
    }

    private String requestNewToken() {
        try {
            InternalCredentialRequestDTO requestDTO = new InternalCredentialRequestDTO();
            requestDTO.setGrantType("client_credentials");
            requestDTO.setClientId(clientId);
            requestDTO.setClientSecret(clientSecret);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<InternalCredentialRequestDTO> request = new HttpEntity<>(requestDTO, headers);

            ResponseEntity<ApiResponse<InternalCredentialResponseDTO>> response = restTemplate.exchange(
                    authServiceUrl + "/auth/token/client-credentials",
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<ApiResponse<InternalCredentialResponseDTO>>() {}
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new BizException(500, "Failed to get internal access token");
            }

            InternalCredentialResponseDTO resBody = response.getBody().getData();
            cachedToken = resBody.getAccessToken(); // cache new token
            return cachedToken;
        } catch (Exception e) {
            throw new BizException(500, "Error request internal token");
        }
    }
}
