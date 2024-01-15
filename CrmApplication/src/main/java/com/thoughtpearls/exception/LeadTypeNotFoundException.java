package com.thoughtpearls.exception;

public class LeadTypeNotFoundException extends RuntimeException{
    public LeadTypeNotFoundException()
    {

    }
    public LeadTypeNotFoundException(String message)
    {
        super(message);
    }
}
