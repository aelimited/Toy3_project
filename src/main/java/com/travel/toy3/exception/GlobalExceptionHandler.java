package com.travel.toy3.exception;

import com.travel.toy3.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.travel.toy3.exception.CustomErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<?>> handleBadRequest(
            Exception e, HttpServletRequest request
    ) {
        log.error("url : {}, message : {}", request.getRequestURI(), e.getMessage());

        return ResponseEntity
                .status(BAD_REQUEST.getCode())
                .body(
                        ApiResponse.builder()
                                .resultCode(BAD_REQUEST.getCode()) // 400
                                .errorMessage(BAD_REQUEST.getMessage()) // 잘못된 요청입니다.
                                .build()
                );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(
            Exception e, HttpServletRequest request
    ) {
        log.error("url : {}, message : {}", request.getRequestURI(), e.getMessage());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR.getCode())
                .body(
                        ApiResponse.builder()
                                .resultCode(INTERNAL_SERVER_ERROR.getCode()) // 500
                                .resultMessage(INTERNAL_SERVER_ERROR.getMessage()) // 서버에 오류가 발생했습니다.
                                .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e
    ) {
        return ResponseEntity
                .status(INCORRECT_JSON_FORMAT.getCode())
                .body(
                        ApiResponse.builder()
                                .resultCode(INCORRECT_JSON_FORMAT.getCode())
                                .resultMessage(INCORRECT_JSON_FORMAT.getMessage())
                                .build()
                );
    }
}
