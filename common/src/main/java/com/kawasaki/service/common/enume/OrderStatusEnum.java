package com.kawasaki.service.common.enume;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    UNPAID((byte) 0, "unpaid order"),
    PAID((byte) 1, "paid order"),
    COMPLETED((byte) 2, "completed order"),
    CANCELLED((byte) 3, "cancelled order");

    private final Byte code;
    private final String msg;

    OrderStatusEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
