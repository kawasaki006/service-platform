package com.kawasaki.service.common.enume;

import lombok.Getter;

@Getter
public enum QuoteStatusEnum {
    PENDING((byte) 0, "pending quote"),
    ACCEPTED((byte) 1, "accepted quote"),
    WITHDRAWN((byte) 2, "withdrawn"),
    TIMEOUT((byte) 3, "timed out");

    private final Byte code;
    private final String msg;

    QuoteStatusEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
