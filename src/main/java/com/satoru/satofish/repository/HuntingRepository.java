package com.satoru.satofish.repository;

import com.satoru.satofish.model.entity.Fish;
import com.satoru.satofish.model.entity.Hunting;
import com.satoru.satofish.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface HuntingRepository extends JpaRepository<Hunting, Long> {

    Optional<Hunting> findByMemberNumAndCompetitionCodeAndFishName(Long memberNum, String competitionCode, String fishName);


    List<Hunting> findByCompetitionCode(String competitionCode);
}



