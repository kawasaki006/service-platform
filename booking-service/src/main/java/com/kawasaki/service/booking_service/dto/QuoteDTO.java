package com.kawasaki.service.booking_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QuoteDTO {
    private Long id;

    private Long requestId;

    private Long userId;

    private Long providerId;

    private BigDecimal price;

    private Byte status;
}
