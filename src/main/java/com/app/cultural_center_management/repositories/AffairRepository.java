package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Affair;
import com.app.cultural_center_management.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AffairRepository extends JpaRepository<Affair, Long> {
    Page<Affair> findAllByOrderBySinceDateDesc(Pageable pageable);
}
