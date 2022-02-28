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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionController {
    @ExceptionHandler(MemberNotExistsException.class)
    public ResponseEntity<ErrorResponse> MemberNotExistsExceptionHandler(HttpServletRequest request, HttpServletResponse response, MemberNotExistsException ex){
        log.info(request.getRequestURI());
        log.error("UserNoExistsException", ex);
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> UserNotFoundExceptionHandler(HttpServletRequest request, HttpServletResponse response, UserNotFoundException ex){
        log.info(request.getRequestURI());
        log.error("UserNotFoundException", ex);
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler(PasswordNotCorrectException.class)
    public ResponseEntity<ErrorResponse> PasswordNotCorrectExceptionHandler(HttpServletRequest request, HttpServletResponse response, PasswordNotCorrectException ex){
        log.info(request.getRequestURI());
        log.error("PasswordNotCorrectException", ex);
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
