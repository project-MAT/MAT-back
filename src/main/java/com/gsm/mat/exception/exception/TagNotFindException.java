package com.gsm.mat.exception.exception;

import com.gsm.mat.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TagNotFindException extends RuntimeException{
    private ErrorCode errorCode;

    public TagNotFindException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
