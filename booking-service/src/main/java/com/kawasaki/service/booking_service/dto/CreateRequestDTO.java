package com.kawasaki.service.booking_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateRequestDTO {
    private Long serviceId;

    private Long providerId;

    @NotBlank
    private Long categoryId;

    @NotBlank
    private Byte timePreference;

    private BigDecimal budget;

    private String note;

    private List<RequestAttrSelectionDTO> attrSelections;
}
