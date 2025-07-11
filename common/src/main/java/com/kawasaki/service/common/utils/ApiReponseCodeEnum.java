package com.kawasaki.service.common.utils;

public enum ApiReponseCodeEnum {
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Wrong params"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Resources not found"),
    CONFLICT(409, "Resources conflict"),
    VALIDATION_ERROR(422, "Validation error"),
    INTERNAL_ERROR(500, "Internal server error");

    private int code;
    private String message;

    ApiReponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
