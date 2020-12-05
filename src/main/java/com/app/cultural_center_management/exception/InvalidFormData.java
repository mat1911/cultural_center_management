package com.app.cultural_center_management.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class InvalidFormData extends RuntimeException{
    private BindingResult bindingResult;

    public InvalidFormData(String message, BindingResult bindingResult){
        super(message);
        this.bindingResult = bindingResult;
    }
}
