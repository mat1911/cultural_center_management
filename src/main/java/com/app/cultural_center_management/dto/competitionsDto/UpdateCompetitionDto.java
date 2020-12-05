package com.app.cultural_center_management.dto.competitionsDto;

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
public class UpdateCompetitionDto {
    private Long id;
    @Size(min = 3, max = 255, message = "Title should be given and should consist of min 3 characters and max 255 characters")
    private String title;
    @Size(min = 3, max = 3000, message = "Description should be given and should consist of min 3 characters and max 3000 characters")
    private String description;
    @MultipartFileValidator(maxSize = 90000, message = "Picture size should be lower than 90KB and acceptable " +
            "extensions are png, jpg, jpeg")
    private MultipartFile picture;
}
