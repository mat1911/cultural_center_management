package com.app.cultural_center_management.dto.careersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetJobOfferDto {
    private Long id;
    private String name;
    private String workingHours;
    private String description;
    private LocalDate sinceDate;
}
