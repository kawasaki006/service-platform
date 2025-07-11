package com.kawasaki.service.common.exception;

import lombok.Data;

@Data
public class BizException extends RuntimeException {
    private int code = 500;
    private String msg = "Unknown server error";

    public BizException(BizExceptionCodeEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
