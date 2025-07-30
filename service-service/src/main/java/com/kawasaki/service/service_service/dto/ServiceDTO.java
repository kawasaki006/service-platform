package com.kawasaki.service.service_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceDTO {
    private Long id;
    private Long providerId;
    private String title;
    private String description;
    private Long categoryId;
    private BigDecimal basePrice;
}
