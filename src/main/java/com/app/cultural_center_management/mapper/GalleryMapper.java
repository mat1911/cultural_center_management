package com.app.cultural_center_management.mapper;

import com.app.cultural_center_management.dto.galleryDto.GetAllGalleryDto;
import com.app.cultural_center_management.entities.Gallery;

import java.util.List;
import java.util.stream.Collectors;

public interface GalleryMapper {

    static List<GetAllGalleryDto> fromGalleryListToGetAllGalleryDtoList(List<Gallery> galleryList) {
        return galleryList.stream()
                .map(gallery -> GetAllGalleryDto.builder()
                        .id(gallery.getId())
                        .pictureUrl(gallery.getPictureUrl())
                        .sinceDate(gallery.getSinceDate())
                        .build())
                .collect(Collectors.toList());
    }
}
