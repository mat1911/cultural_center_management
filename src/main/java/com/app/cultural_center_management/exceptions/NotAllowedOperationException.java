package com.app.cultural_center_management.exceptions;

public class NotAllowedOperationException extends RuntimeException {
    public NotAllowedOperationException(String message){
        super(message);
    }
}
