package com.app.cultural_center_management.dto.galleryDto;

import com.app.cultural_center_management.validators.MultipartFileValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGalleryDto {
    @NotNull(message = "Picture must be given!")
    @MultipartFileValidator(message = "Picture size should be lower than 90KB and acceptable " +
            "extensions are png, jpg, jpeg",maxSize = 90000)
    private MultipartFile picture;
}
