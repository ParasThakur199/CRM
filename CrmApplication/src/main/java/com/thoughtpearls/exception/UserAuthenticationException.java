package com.thoughtpearls.exception;

public class UserAuthenticationException extends RuntimeException{
    public UserAuthenticationException(){}

    public UserAuthenticationException(String message){
        super(message);
    }
}
