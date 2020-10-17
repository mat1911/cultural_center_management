package com.app.cultural_center_management.service;

import com.app.cultural_center_management.dto.galleryDto.GetAllGalleryDto;
import com.app.cultural_center_management.dto.galleryDto.UpdateGalleryDto;
import com.app.cultural_center_management.entities.Gallery;
import com.app.cultural_center_management.exceptions.ObjectNotFoundException;
import com.app.cultural_center_management.mapper.GalleryMapper;
import com.app.cultural_center_management.repositories.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final DropboxService dropboxService;

    public Page<GetAllGalleryDto> getAllGalleries(int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Gallery> page = galleryRepository.findAllByOrderBySinceDateDesc(pageRequest);
        List<GetAllGalleryDto> resultContent = GalleryMapper.fromGalleryListToGetAllGalleryDtoList(page.getContent());

        return new PageImpl<>(resultContent, pageRequest, page.getTotalElements());
    }

    public Long addNewPicture(UpdateGalleryDto updateGalleryDto){
        Gallery gallery = new Gallery();
        gallery.setSinceDate(LocalDate.now());
        gallery.setPictureUrl(dropboxService.uploadFile(updateGalleryDto.getPicture()));
        return galleryRepository.save(gallery).getId();
    }

    public Long deletePicture(Long pictureId){
        Gallery gallery = galleryRepository.findById(pictureId)
                .orElseThrow(() -> new ObjectNotFoundException("Picture with given id does not exist!"));
        dropboxService.deleteFile(gallery.getPictureUrl());
        galleryRepository.delete(gallery);
        return gallery.getId();
    }
}
