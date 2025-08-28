package com.kawasaki.service.common.enume;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    READY((byte) 0, "ready order"),
    CONFIRMED((byte) 1, "confirmed order"),
    PAID((byte) 2, "paid order"),
    COMPLETED((byte) 3, "completed order"),
    CANCELLED((byte) 4, "cancelled order");

    private final Byte code;
    private final String msg;

    OrderStatusEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
