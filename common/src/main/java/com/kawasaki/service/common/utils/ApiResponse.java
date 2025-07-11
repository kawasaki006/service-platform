package com.kawasaki.service.common.utils;

import com.kawasaki.service.common.exception.BizExceptionCodeEnum;

public class ApiResponse<T> {
    private int code;

    private String message;

    private T data;

    public ApiResponse() {}

    public ApiResponse(int code, String message) {
        this(code, message, null);
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ApiReponseCodeEnum.SUCCESS.getCode(), ApiReponseCodeEnum.SUCCESS.getMessage());
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiReponseCodeEnum.SUCCESS.getCode(), ApiReponseCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> fail(ApiReponseCodeEnum resultCode) {
        return new ApiResponse<>(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message);
    }

    public static <T> ApiResponse<T> fail(ApiReponseCodeEnum resultCode, T data) {
        return new ApiResponse<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(BizExceptionCodeEnum errorEnum) {
        return new ApiResponse<>(errorEnum.getCode(), errorEnum.getMsg(), null);
    }

    public static <T> ApiResponse<T> error(int code, String msg) {
        return new ApiResponse<>(code, msg, null);
    }

    public static <T> ApiResponse<T> error(int code, String msg, T data) {
        return new ApiResponse<>(code, msg, data);
    }

    // --- Getter & Setter ---
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
