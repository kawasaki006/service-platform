package com.kawasaki.service.service_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateServiceRequestDTO {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private Long categoryId;

    private BigDecimal basePrice;

    private List<AttrDTO> attrs;
}
