package com.app.cultural_center_management.controllers.admin;

import com.app.cultural_center_management.dto.galleryDto.UpdateGalleryDto;
import com.app.cultural_center_management.exceptions.InvalidFormData;
import com.app.cultural_center_management.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/gallery")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminGalleryController {

    private final GalleryService galleryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Long addNewPicture(@Valid @ModelAttribute UpdateGalleryDto updateGalleryDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){ throw new InvalidFormData("Data in form is invalid", bindingResult); }
        return galleryService.addNewPicture(updateGalleryDto);
    }

    @DeleteMapping("/{pictureId}")
    @ResponseStatus(HttpStatus.OK)
    public Long deletePicture(@PathVariable Long pictureId){
        return galleryService.deletePicture(pictureId);
    }
}
