package com.app.cultural_center_management.dto.newsDto;

import com.app.cultural_center_management.validators.MultipartFileValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateNewsDto {
    private Long id;
    @Size(min = 3, message = "Title should contain at least 3 letters")
    private String title;
    @Size(min = 3, max = 1000, message = "Description should contain at least 3 letters")
    private String description;
    @Size(min = 3, max = 1000, message = "Short description should contain at least 3 letters")
    private String shortDescription;
    @MultipartFileValidator(maxSize = 90000, message = "Picture size should be lower than 90KB and acceptable " +
            "extensions are png, jpg, jpeg")
    private MultipartFile picture;
}
