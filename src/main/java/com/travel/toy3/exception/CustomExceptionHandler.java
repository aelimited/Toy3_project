package com.travel.toy3.exception;

import com.travel.toy3.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.travel.toy3.exception.CustomErrorCode.BAD_REQUEST;
import static com.travel.toy3.exception.CustomErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@Order(1)
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e, HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}", e.getCustomErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return ResponseEntity
                .status(e.getCustomErrorCode().getCode())
                .body(
                        ApiResponse.builder()
                                .resultCode(e.getCustomErrorCode().getCode())
                                .errorMessage(e.getDetailMessage())
                                .build()
                );
    }
}
