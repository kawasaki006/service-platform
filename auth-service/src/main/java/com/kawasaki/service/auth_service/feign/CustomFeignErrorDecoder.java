package com.kawasaki.service.auth_service.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.common.utils.ApiResponse;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Exception decode(String s, Response response) {
        try {
            String body = Util.toString(response.body().asReader());
            ApiResponse error = objectMapper.readValue(body, ApiResponse.class);

            if (error.getCode() == BizExceptionCodeEnum.INVALID_EMAIL_PASSWORD.getCode()) {
                return new BizException(BizExceptionCodeEnum.INVALID_EMAIL_PASSWORD);
            }
            if (error.getCode() == BizExceptionCodeEnum.EMAIL_EXISTS.getCode()) {
                return new BizException(BizExceptionCodeEnum.EMAIL_EXISTS);
            }

            return new BizException(500, "Unknown Server Error" + error.getMessage());
        } catch (Exception e) {
            byte[] fallback = e.getMessage().getBytes();
            return new FeignException.InternalServerError("Failed to parse feign exception", response.request(), fallback, null);
        }
    }
}
