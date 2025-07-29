package com.kawasaki.service.common.dto;

import lombok.Data;

@Data
public class InternalCredentialRequestDTO {
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String scope;
}
