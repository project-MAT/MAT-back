package com.gsm.mat.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String details;

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.details = errorCode.getDetails();
    }
}
