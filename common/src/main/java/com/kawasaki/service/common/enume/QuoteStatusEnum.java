package com.kawasaki.service.common.enume;

public enum QuoteStatusEnum {
    PENDING((byte) 0, "pending quote"),
    ACCEPTED((byte) 1, "accepted quote");
    private byte code;
    private String msg;

    QuoteStatusEnum(Byte code, String msg) {
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
