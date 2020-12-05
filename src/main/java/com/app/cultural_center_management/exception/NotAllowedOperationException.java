package com.app.cultural_center_management.exception;

public class NotAllowedOperationException extends RuntimeException {
    public NotAllowedOperationException(String message){
        super(message);
    }
}
