package com.kawasaki.service.booking_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateQuoteDTO {
    @NotBlank
    private Long requestId;

    @NotBlank
    private Long userId;

    @NotBlank
    private BigDecimal price;

    private String note;
}
