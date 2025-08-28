package com.kawasaki.service.common.enume;

import lombok.Getter;

@Getter
public enum RequestStatusEnum {
    OPEN((byte) 0, "open request"),
    CLOSED((byte) 1, "close request"),
    CANCELLED((byte) 2, "cancel request"),
    TIMEOUT((byte) 3, "request timeout");

    private final Byte code;
    private final String msg;

    RequestStatusEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
