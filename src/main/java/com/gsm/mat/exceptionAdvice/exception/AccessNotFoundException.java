package com.gsm.mat.exceptionAdvice.exception;

public class AccessNotFoundException extends RuntimeException{
    public AccessNotFoundException(String msg, Throwable t){
        super(msg, t);
    }
    public AccessNotFoundException(String msg){
        super(msg);
    }
    public AccessNotFoundException(){
        super();
    }
}
