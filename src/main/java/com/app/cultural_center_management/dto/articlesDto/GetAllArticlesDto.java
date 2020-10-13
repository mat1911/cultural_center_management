package com.app.cultural_center_management.dto.articlesDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllArticlesDto {
    private Long id;
    private LocalDate sinceDate;
    private String pictureUrl;
    private String title;
    private Double rate;
}
