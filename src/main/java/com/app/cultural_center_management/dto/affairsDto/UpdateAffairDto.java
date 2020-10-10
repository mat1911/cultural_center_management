package com.app.cultural_center_management.dto.affairsDto;

import com.app.cultural_center_management.validators.MultipartFileValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAffairDto {
    @Size(min = 3, max = 255, message = "Title should has min 3 characters and max 255 characters")
    private String title;
    @Size(min = 3, max = 1000, message = "Title should has min 3 characters and max 255 characters")
    private String shortDescription;
    @MultipartFileValidator(message = "Picture size should be lower than 90KB and acceptable " +
            "extensions are png, jpg, jpeg",maxSize = 90000)
    private MultipartFile picture;
    @Min(value = 1)
    private Long availableSeats;
}