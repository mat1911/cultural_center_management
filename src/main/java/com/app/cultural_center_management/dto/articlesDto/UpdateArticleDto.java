package com.app.cultural_center_management.dto.articlesDto;

import com.app.cultural_center_management.validator.MultipartFileValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleDto {
    private Long id;
    @Size(min = 3, message = "Title should contain at least 3 letters")
    private String title;
    @Size(min = 1, max = 5000, message = "Content should be given and should contain max 5000 characters")
    private String content;
    @MultipartFileValidator(maxSize = 90000, message = "Picture size should be lower than 90KB and acceptable " +
            "extensions are png, jpg, jpeg")
    private MultipartFile picture;
}
