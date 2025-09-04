package com.kawasaki.service.order_service.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderConfirmVO {
    private Long requestId;
    private Long quoteId;
    private Long serviceId;
    private BigDecimal price;

    // token for submit order
    private String orderToken;
}
