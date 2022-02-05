package com.gsm.mat.exception.exception;

import com.gsm.mat.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MemberNotExistsException extends RuntimeException{
    private final ErrorCode errorCode;

    public MemberNotExistsException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
