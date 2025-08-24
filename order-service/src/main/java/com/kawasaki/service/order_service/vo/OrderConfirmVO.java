package com.kawasaki.service.order_service.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderConfirmVO {
    Long userId;
    Long quoteId;
    Long serviceId;
    BigDecimal price;

    // token for submit order
    String orderToken;
}
