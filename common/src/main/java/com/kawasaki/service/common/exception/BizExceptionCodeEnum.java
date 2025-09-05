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
    DB_UPDATE_FAILED(10003, "DB update failed"),

    INVALID_EMAIL_PASSWORD(12000, "Invalid email or password"),
    BAD_TOKEN(12001, "Invalid or expired token"),
    EMAIL_EXISTS(12002, "Email already exists"),

    FAIL_TO_SAVE_ES_DOC(13001, "Failed to save es doc"),
    INVALID_SERVICE_ID_ES(13002, "Failed to find service by the given service id"),

    INVALID_REQUEST(14001, "Request does not exist or is not open"),

    INVALID_OR_EMPTY_ORDER_TOKEN(15001, "Invalid or empty order token"),
    ORDER_CREATE_DB_INSERT_EXCEPTION(15002, "Order creation failed, db insertion error"),
    ORDER_CANCEL_EXCEPTION(15003, "Order cancel failed"),
    CREATE_STRIPE_PAYMENT_EXCEPTION(15004, "Create stripe payment failed"),
    INVALID_STRIPE_SIGNATURE(15005, "Invalid stripe signature"),
    UNHANDLED_STRIPE_PAYMENT_EVENT(15006, "Unhandled stripe payment event"),
    STRIPE_DESERIALIZER_ERROR(15007, "Stripe deserializer error"),
    ORDER_DOES_NOT_EXIST(15008, "Order does not exist"),;


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
