package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface NewsRepository extends PagingAndSortingRepository<News, Long> {
    Page<News> findAllByOrderByDateOfAddDesc(Pageable pageable);
}
