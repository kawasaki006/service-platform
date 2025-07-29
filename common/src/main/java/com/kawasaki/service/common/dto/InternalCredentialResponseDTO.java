package com.kawasaki.service.common.dto;

import lombok.Data;

@Data
public class InternalCredentialResponseDTO {
    private String accessToken;
    private String scope;
}
