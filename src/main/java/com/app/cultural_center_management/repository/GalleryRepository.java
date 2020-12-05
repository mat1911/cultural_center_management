package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Page<Gallery> findAllByOrderBySinceDateDesc(Pageable pageable);
}
