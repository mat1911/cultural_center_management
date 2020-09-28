package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.securityDto.AppError;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@CrossOrigin(origins = "http://localhost:4200")
public class ExceptionController {

    @ExceptionHandler({SecurityException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError handleAppSecurityException(Exception e) {
        return new AppError("Internal server error occurred", 500);
    }

    @ExceptionHandler(InvalidFormData.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public AppError handleValidationExceptions(InvalidFormData ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));

        return new AppError(errorMessage, 422);
    }
}
