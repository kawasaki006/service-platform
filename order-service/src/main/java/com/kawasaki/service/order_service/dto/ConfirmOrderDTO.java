package com.kawasaki.service.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConfirmOrderDTO {
    private Long requestId;
    private Long quoteId;
    private Long serviceId;
    private BigDecimal price;
}
