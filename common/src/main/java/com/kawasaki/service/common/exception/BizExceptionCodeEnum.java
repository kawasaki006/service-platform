package com.kawasaki.service.common.exception;

/*
* 10 - general
* 11 - user
* 12 - provider
* 13 - service
* 14 - booking
* 15 - order
*
*/
public enum BizExceptionCodeEnum {
    INVALID_REQUEST_PARAMS(10001, "Invalid request params"),
    DB_INSERT_FAILED(10002, "DB insertion failed"),
    INVALID_EMAIL_PASSWORD(12000, "Invalid email or password"),
    BAD_TOKEN(12001, "Invalid or expired token"),
    EMAIL_EXISTS(12002, "Email already exists"),
    FAIL_TO_SAVE_ES_DOC(13001, "Failed to save es doc"),
    INVALID_SERVICE_ID_ES(13002, "Failed to find service by the given service id");


    private int code;
    private String msg;

    BizExceptionCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
