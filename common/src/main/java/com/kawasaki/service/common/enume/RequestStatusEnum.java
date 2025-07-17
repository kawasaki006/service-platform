package com.kawasaki.service.common.enume;

public enum RequestStatusEnum {
    OPEN((byte) 0, "open request"),
    CLOSED((byte) 1, "close request");
    private Byte code;
    private String msg;

    RequestStatusEnum(Byte code, String msg) {
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
