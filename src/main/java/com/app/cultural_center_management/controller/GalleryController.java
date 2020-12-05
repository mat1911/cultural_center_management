package com.app.cultural_center_management.controller;

import com.app.cultural_center_management.dto.ResponseData;
import com.app.cultural_center_management.dto.galleryDto.GetAllGalleryDto;
import com.app.cultural_center_management.dto.galleryDto.UpdateGalleryDto;
import com.app.cultural_center_management.exception.InvalidFormData;
import com.app.cultural_center_management.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
@CrossOrigin(origins = "http://localhost:4200")
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long addNewPicture(@Valid @ModelAttribute UpdateGalleryDto updateGalleryDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return galleryService.addNewPicture(updateGalleryDto);
    }

    @DeleteMapping("/{pictureId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Long deletePicture(@PathVariable Long pictureId){
        return galleryService.deletePicture(pictureId);
    }

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
