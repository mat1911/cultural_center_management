package com.app.cultural_center_management.dto.careersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJobOfferDto {
    @Size(min = 1, message = "Job name should be given and should consist of max 255 characters")
    private String name;
    @Size(min = 1, message = "Working hours should be given and should consist of max 255 characters")
    private String workingHours;
    @Size(min = 1, max = 5000, message = "Description should be given and should consist of max 5000 characters")
    private String description;
}
