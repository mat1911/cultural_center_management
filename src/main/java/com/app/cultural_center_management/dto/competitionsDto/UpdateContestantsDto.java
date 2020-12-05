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
public class UpdateContestantsDto {
    @Size(min = 1, max = 3000, message = "User comment should be given and should consist of max 3000 characters")
    private String userComment;
    @MultipartFileValidator(maxSize = 1000000, extension = "pdf docx", message = "User file size should be lower than 1MB " +
            "and acceptable extensions are pdf, docx")
    private MultipartFile userFile;
}
