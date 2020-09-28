package com.app.cultural_center_management.validators;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class MultipartFileValidatorImpl implements ConstraintValidator<MultipartFileValidator, MultipartFile> {

    private String extension;
    private int maxSize;

    @Override
    public void initialize(MultipartFileValidator constraintAnnotation) {
        extension = constraintAnnotation.extension();
        maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(file)) {
            return true;
        }

        String givenExtension = file.getOriginalFilename().split("\\.")[1];
        List<String> expectedExtensions = List.of(extension
                .trim()
                .replaceAll("\\s+", " ")
                .split(" "));

        if (!expectedExtensions.contains(givenExtension)) {
            return false;
        }

        return file.getSize() <= maxSize;
    }
}
