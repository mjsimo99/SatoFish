package com.satoru.satofish.repository;

import com.satoru.satofish.model.entity.Competition;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository

public interface CompetitionRepository extends JpaRepository<Competition, String> {


    Page<Competition> findAll(Pageable pageable);

    Page<Competition> findByDate(LocalDate date, Pageable pageable);

    Page<Competition> findByDateBefore(LocalDate date, Pageable pageable);

    Page<Competition> findByDateAfter(LocalDate date, Pageable pageable);

}
