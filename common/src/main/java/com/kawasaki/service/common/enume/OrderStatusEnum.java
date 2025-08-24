package com.kawasaki.service.common.enume;

public enum OrderStatusEnum {
    READY((byte) 0, "ready order"),
    CONFIRMED((byte) 1, "confirmed order"),
    PAID((byte) 2, "paid order"),
    COMPLETED((byte) 3, "completed order"),
    CANCELLED((byte) 4, "cancelled order");

    private byte code;
    private String msg;

    OrderStatusEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Byte getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
