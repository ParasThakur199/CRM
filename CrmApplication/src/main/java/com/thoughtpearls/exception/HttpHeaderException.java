package com.thoughtpearls.exception;

public class HttpHeaderException extends RuntimeException{
    public HttpHeaderException(){}
    public HttpHeaderException(String message){
        super(message);
    }
}
