package com.gsm.mat.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;
    private String details;

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.details = errorCode.getDetails();
    }
}
