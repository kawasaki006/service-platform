package com.kawasaki.service.common.events;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCreateEvent implements Serializable {
    private Long orderId;

    private Long requestId;

    private Long quoteId;
}
