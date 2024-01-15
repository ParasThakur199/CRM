package com.thoughtpearls.exception;

import lombok.AllArgsConstructor;

public class StatusNotFoundException extends RuntimeException{
    public StatusNotFoundException()
    {

    }
    public StatusNotFoundException(String message)
    {
        super(message);
    }
}
