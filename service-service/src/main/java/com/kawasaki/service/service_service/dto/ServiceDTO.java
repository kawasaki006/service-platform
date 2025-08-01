package com.kawasaki.service.service_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ServiceDTO {
    private Long id;
    private Long providerId;
    private String title;
    private String description;
    private Long categoryId;
    private BigDecimal basePrice;
    private List<AttrDTO> attrs;
}
