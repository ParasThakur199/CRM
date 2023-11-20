package com.thoughtpearls.exception;

public class CommentCreationException extends RuntimeException{
    public CommentCreationException(String message) {
        super(message);
    }
    public CommentCreationException() {}
}
