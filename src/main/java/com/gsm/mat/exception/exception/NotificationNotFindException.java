package com.gsm.mat.exception.exception;

import com.gsm.mat.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotificationNotFindException extends RuntimeException{
    private ErrorCode errorCode;

    public NotificationNotFindException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
