package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.securityDto.AppError;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import io.jsonwebtoken.ExpiredJwtException;
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

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError handleAppSecurityException(SecurityException e) {
        return new AppError(e.getMessage(), 401);
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError handleException(Exception ex){
        ex.printStackTrace();
        System.out.println(ex.getMessage());
        return new AppError("Internal server error occurred", 500);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AppError handleException(ExpiredJwtException ex){
        System.out.println("PROBLEM");
        return new AppError("User should be authenticated anew " +
                "or his token should be refreshed", 401);
    }
}
