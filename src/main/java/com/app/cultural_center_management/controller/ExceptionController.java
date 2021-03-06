package com.app.cultural_center_management.controller;

import com.app.cultural_center_management.dto.AppError;
import com.app.cultural_center_management.exception.InvalidFormData;
import com.app.cultural_center_management.exception.NotAllowedOperationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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
        return new AppError("Internal server error occurred", 500);
    }

    @ExceptionHandler(NotAllowedOperationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public AppError handleNotAllowedOperationException(NotAllowedOperationException ex){
        return new AppError(ex.getMessage(), 409);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppError handleAccessDeniedException(AccessDeniedException ex){
        return new AppError(ex.getMessage(), 403);
    }
}