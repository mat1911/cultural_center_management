package com.app.cultural_center_management.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented //to ensure that classes using your annotation show this in their generated JavaDoc
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartFileValidatorImpl.class)
public @interface MultipartFileValidator {
    String message() default "Multipart File Validation Error";
    String extension() default "jpg jpeg png";
    int maxSize() default 20000;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
