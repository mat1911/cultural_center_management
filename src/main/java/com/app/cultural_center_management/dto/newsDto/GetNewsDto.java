package com.app.cultural_center_management.dto.newsDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetNewsDto {
    private String title;
    private String pictureUrl;
    private String description;
    private LocalDate dateOfAdd;
}
