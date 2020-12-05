package com.app.cultural_center_management.repository;

import com.app.cultural_center_management.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT application FROM Application application " +
            "WHERE CONCAT(application.user.name, application.user.surname) LIKE %?1% " +
            "AND application.jobOffer.id = ?2 ORDER BY application.sinceDate")
    Page<Application> findAllFilteredByJobOffer_Id(Pageable pageable, Long jobOfferId, String keyword);
    Page<Application> findAllByJobOffer_Id(Pageable pageable, Long jobOfferId);
    Optional<Application> findByUser_IdAndJobOffer_Id(Long userId, Long jobOfferId);
}
