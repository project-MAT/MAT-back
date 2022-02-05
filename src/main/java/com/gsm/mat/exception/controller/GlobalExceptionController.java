package com.gsm.mat.exception.controller;

import com.gsm.mat.exception.ErrorResponse;
import com.gsm.mat.exception.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionController {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNoExistsException(MemberNotExistsException ex){
        log.error("UserNoExistsException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> UserNotFoundExceptionHandler(UserNotFoundException ex){
        log.error("UserNotFoundException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
