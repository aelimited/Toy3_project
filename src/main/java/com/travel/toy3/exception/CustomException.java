package com.travel.toy3.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final CustomErrorCode customErrorCode;
    private final String detailMessage;

    public CustomException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
        this.customErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public CustomException(CustomErrorCode errorCode, String message) {
        super(message);
        this.customErrorCode = errorCode;
        this.detailMessage = message;
    }
}
