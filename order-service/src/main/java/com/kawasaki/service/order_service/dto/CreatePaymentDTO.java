package com.kawasaki.service.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentDTO {
    private BigDecimal amount;

    private Long orderId;
}
