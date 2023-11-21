package com.thoughtpearls.exception;

public class CommentsNotFoundException extends RuntimeException{
    public CommentsNotFoundException(){};

    public CommentsNotFoundException(String message){
        super(message);
    }
}