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
    DIARY_NOT_EXISTS(404, "Can't find diary by id", ErrorClassification.COMMON+"-ERR-404"),
    USER_NOT_FOUND(401, "User Not Found", ErrorClassification.MEMBER+"-ERR-401"),
    NOT_SAME_TARGET(401, "Target Not Same", ErrorClassification.MEMBER+"-ERR-401"),
    MEMBER_NOT_SAME(401, "Member Not Same", ErrorClassification.MEMBER+"-ERR-401"),
    FILE_NOT_EXIST(404, "Can't find file",ErrorClassification.COMMON+"-ERR-404"),
    WRONG_PATH(404, "Path isn't right", ErrorClassification.COMMON+"-ERR-404"),
    INVALID_TOKEN(401, "Invalid Token", ErrorClassification.MEMBER+"-ERR-401"),
    ;
    private int status;
    private String message;
    private String details;
}
