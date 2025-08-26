package com.kawasaki.service.common.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/*
* 10 - general
* 11 - user
* 12 - provider
* 13 - service
* 14 - booking
* 15 - order
*
*/
@Getter
public enum BizExceptionCodeEnum {
    INVALID_REQUEST_PARAMS(10001, "Invalid request params"),
    DB_INSERT_FAILED(10002, "DB insertion failed"),

    INVALID_EMAIL_PASSWORD(12000, "Invalid email or password"),
    BAD_TOKEN(12001, "Invalid or expired token"),
    EMAIL_EXISTS(12002, "Email already exists"),

    FAIL_TO_SAVE_ES_DOC(13001, "Failed to save es doc"),
    INVALID_SERVICE_ID_ES(13002, "Failed to find service by the given service id"),

    INVALID_OR_EMPTY_ORDER_TOKEN(14001, "Invalid or empty order token");


    private final int code;
    private final String msg;

    BizExceptionCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static final Map<Integer, BizExceptionCodeEnum> CODE_MAP =
            Arrays.stream(BizExceptionCodeEnum.values())
                    .collect(Collectors.toMap(BizExceptionCodeEnum::getCode, e -> e));

    public static BizExceptionCodeEnum fromCode(int code) {
        return CODE_MAP.get(code);
    }
}
