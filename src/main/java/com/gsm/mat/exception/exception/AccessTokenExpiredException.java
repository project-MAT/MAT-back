package com.gsm.mat.exception.exception;


import com.gsm.mat.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AccessTokenExpiredException extends RuntimeException {
    private final ErrorCode errorCode;

    public AccessTokenExpiredException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
