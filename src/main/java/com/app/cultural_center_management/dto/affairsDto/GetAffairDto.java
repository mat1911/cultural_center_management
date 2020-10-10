package com.app.cultural_center_management.dto.affairsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAffairDto {
    private String title;
    private String shortDescription;
    private Long availableSeats;
    private String ownerName;
    private String ownerSurname;
}
