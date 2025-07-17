package com.kawasaki.service.booking_service.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private Long id;

    private Long userId;

    private Long serviceId;

    private Long providerId;

    private Long categoryId;

    private String note;

    private Byte status;
}
