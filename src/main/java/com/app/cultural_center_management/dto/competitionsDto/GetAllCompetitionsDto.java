package com.app.cultural_center_management.dto.competitionsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCompetitionsDto {
    private Long id;
    private String title;
    private String pictureUrl;
    private LocalDate sinceDate;
}
