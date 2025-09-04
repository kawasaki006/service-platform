package com.kawasaki.service.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubmitOrderDTO {
    private Long requestId;
    private Long quoteId;
    private Long serviceId;
    private BigDecimal price;

    // order token
    private String orderToken;
}
