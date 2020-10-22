package com.app.cultural_center_management.dto.affairsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllUserAffairs {
    private Long id;
    private String title;
    private String shortDescription;
    private String description;
    private String pictureUrl;
}
