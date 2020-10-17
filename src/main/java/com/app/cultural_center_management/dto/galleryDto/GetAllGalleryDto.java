package com.app.cultural_center_management.dto.galleryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllGalleryDto {
    private Long id;
    private String pictureUrl;
    private LocalDate sinceDate;
}
