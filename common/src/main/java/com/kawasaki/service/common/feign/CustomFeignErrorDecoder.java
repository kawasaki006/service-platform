package com.kawasaki.service.common.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.common.utils.ApiResponse;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.util.Objects;

public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Exception decode(String s, Response response) {
        try {
            String body = Util.toString(response.body().asReader());
            ApiResponse error = objectMapper.readValue(body, ApiResponse.class);

            BizExceptionCodeEnum matched = BizExceptionCodeEnum.fromCode(error.getCode());
            if (!Objects.isNull(matched)) {
                return new BizException(matched);
            }

            return new BizException(500, "Unknown Server Error" + error.getMessage());
        } catch (Exception e) {
            byte[] fallback = e.getMessage().getBytes();
            return new FeignException.InternalServerError("Failed to parse feign exception", response.request(), fallback, null);
        }
    }
}
