package com.kawasaki.service.booking_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateRequestDTO {
    private Long serviceId;

    private Long providerId;

    @NotBlank
    private Long categoryId;

    private String note;

    private List<RequestAttrSelectionDTO> attrSelections;
}
