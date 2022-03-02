package com.gsm.mat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNKNOWN_ERROR(500, "Unknown Error", ErrorClassification.SERVER + "-ERR-500"),
    BAD_REQUEST(400, "Bad Request", ErrorClassification.COMMON+"-ERR-400"),
    UNAUTHORIZED(401, "Unauthorized", ErrorClassification.COMMON+"-ERR-401"),
    FORBIDDEN(403, "Forbidden", ErrorClassification.COMMON+"-ERR-403"),
    MEMBER_NOT_EXISTS(404,"Can't find member by id", ErrorClassification.MEMBER+"-ERR-404"),
    USER_NOT_FOUND(401, "User Not Found", ErrorClassification.MEMBER+"-ERR-401"),
    INVALID_TOKEN(401, "Invalid Token", ErrorClassification.MEMBER+"-ERR-401"),
    PASSWORD_NOT_MATCH(403, "Password Is Not Correct", ErrorClassification.MEMBER+"-ERR-403"),
    CAN_NOT_MINUS(400, "Can't minus notification's goods", ErrorClassification.COMMON+"-ERR-400")
    ;
    private int status;
    private String message;
    private String details;
}
