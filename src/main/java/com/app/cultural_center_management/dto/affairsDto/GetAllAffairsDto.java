package com.app.cultural_center_management.dto.affairsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllAffairsDto {
    private Long id;
    private String title;
    private String shortDescription;
    private String description;
    private Double rate;
    private LocalDate sinceDate;
    private Long availableSeats;
    private String pictureUrl;
}
