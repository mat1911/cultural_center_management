package com.app.cultural_center_management.dto.careersDto;

import com.app.cultural_center_management.validator.MultipartFileValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationDto {
    @Size(max = 1500, message = "User comment length should be less than 1500 characters")
    private String userComment;

    @NotNull(message = "File should be given")
    @MultipartFileValidator(maxSize = 1000000, extension = "docx pdf", message = "User file size should be lower than 1MB " +
            "and acceptable extensions are pdf, docx")
    private MultipartFile userFile;
}
