package com.kawasaki.service.booking_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetRequestOfCategoryDTO {
    @NotBlank
    private Long categoryId;

    private Long serviceId;
}
