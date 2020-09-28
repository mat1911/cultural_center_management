package com.app.cultural_center_management.dto.securityDto.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllNewsDto {
    private Long id;
    private String title;
    private String shortDescription;
    private String pictureUrl;
    private LocalDate dateOfAdd;
}
