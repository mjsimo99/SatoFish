package com.satoru.satofish.repository;

import com.satoru.satofish.model.entity.Competition;
import com.satoru.satofish.model.entity.Member;
import com.satoru.satofish.model.entity.Ranking;
import com.satoru.satofish.model.identity.RankingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, RankingId> {



    Optional<Ranking> findByMemberAndCompetition(Member member, Competition competition);


    long countParticipantsByCompetition(Competition competition);


    @Query("SELECT r FROM Ranking r WHERE r.id.competition = :competition ORDER BY r.score DESC")
    List<Ranking> findByCompetitionOrderByScoreDesc(@Param("competition") Competition competition);

}
