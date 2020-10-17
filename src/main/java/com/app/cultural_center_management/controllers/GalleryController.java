package com.app.cultural_center_management.controllers;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.careersDto.GetAllJobOffersDto;
import com.app.cultural_center_management.dto.galleryDto.GetAllGalleryDto;
import com.app.cultural_center_management.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
@CrossOrigin(origins = "http://localhost:4200")
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<GetAllGalleryDto>> getGallery(@RequestParam int pageSize, @RequestParam int pageNumber){
        Page<GetAllGalleryDto> resultPage = galleryService
                .getAllGalleries(pageNumber - 1, pageSize);

        return ResponseData
                .<List<GetAllGalleryDto>>builder()
                .data(resultPage.getContent())
                .fullContentSize(resultPage.getTotalElements())
                .build();
    }
}
