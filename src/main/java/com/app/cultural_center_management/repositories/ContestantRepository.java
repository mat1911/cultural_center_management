package com.app.cultural_center_management.repositories;

import com.app.cultural_center_management.entities.Contestant;
import com.app.cultural_center_management.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContestantRepository extends JpaRepository<Contestant, Long> {

    @Query("SELECT contestant FROM Contestant contestant WHERE CONCAT(contestant.user.name, " +
            "contestant.user.surname) LIKE %?1% AND contestant.competition.id = ?2 " +
            "AND contestant.isAccepted = true")
    Page<Contestant> findAllByCompetitionIdAndFiltered(Pageable pageable, String keyword, Long competitionId);
    Page<Contestant> findAllByIsAcceptedTrueAndCompetition_Id(Pageable pageable, Long competitionId);
    Page<Contestant> findAllByIsAcceptedFalseAndCompetition_Id(Pageable pageable, Long competitionId);
    Boolean existsByCompetition_IdAndUser_Id(Long competitionId, Long userId);
    Optional<Contestant> findByCompetition_IdAndUser_Id(Long competitionId, Long userId);
}
