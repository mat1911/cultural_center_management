package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.Affair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffairRepository extends JpaRepository<Affair, Long> {
    Page<Affair> findAllByOrderBySinceDateDesc(Pageable pageable);
}
