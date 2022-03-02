package com.gsm.mat.exception.exception;

import com.gsm.mat.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotMinusGoodsException extends RuntimeException{
    private final ErrorCode errorCode;

    public NotMinusGoodsException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
