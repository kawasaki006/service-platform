package com.kawasaki.service.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubmitOrderDTO {
    Long userId;
    Long quoteId;
    Long serviceId;
    BigDecimal price;

    // order token
    String orderToken;
}
