package com.thoughtpearls.exception;

public class LeadNotFoundException extends RuntimeException{
    public LeadNotFoundException(){}

    public LeadNotFoundException(String message){
        super(message);
    }
}
