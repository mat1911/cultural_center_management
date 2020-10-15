package com.app.cultural_center_management.dto.competitionsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCompetitionDto {
    private Long id;
    private String title;
    private String description;
}
