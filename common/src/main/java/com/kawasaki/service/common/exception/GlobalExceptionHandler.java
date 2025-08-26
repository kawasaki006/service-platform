package com.kawasaki.service.common.exception;

import com.kawasaki.service.common.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
        log.info("Global exception handler registered!");
    }

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiResponse<?>> bizExceptionHandler(BizException e) {
        log.error("Biz exception caught: " + e.getMsg());
        return new ResponseEntity<>(ApiResponse.error(e.getCode(), e.getMsg()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> validationExceptionHandler(MethodArgumentNotValidException e) {
        log.error("Validation exception caught: " + e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(ApiResponse.error(
                BizExceptionCodeEnum.INVALID_REQUEST_PARAMS.getCode(),
                BizExceptionCodeEnum.INVALID_REQUEST_PARAMS.getMsg(),
                errorMap), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> exceptionHandler(Exception e) {
        log.error("Unknown exception caught: " + e.getMessage());
        return new ResponseEntity<>(ApiResponse.error(500, "Unknown server error: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
