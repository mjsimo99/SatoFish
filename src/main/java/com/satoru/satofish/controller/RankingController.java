package com.satoru.satofish.controller;

import com.satoru.satofish.model.dto.RankingDTO;
import com.satoru.satofish.model.dto.response.RankingRespDTO;
import com.satoru.satofish.model.entity.Member;
import com.satoru.satofish.model.entity.Competition;
import com.satoru.satofish.model.identity.RankingId;
import com.satoru.satofish.service.HuntingService;
import com.satoru.satofish.service.RankingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rankings")
@CrossOrigin
public class RankingController {

    @Autowired
    private RankingService rankingService;




    @GetMapping("/podium/{competitionCode}")
    public ResponseEntity<List<RankingRespDTO>> calculatePodium(@PathVariable String competitionCode) {
        List<RankingRespDTO> podium = rankingService.calculatePodium(competitionCode);
        return podium != null && !podium.isEmpty()
                ? new ResponseEntity<>(podium, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<RankingDTO> createRanking(@Valid @RequestBody RankingDTO rankingDTO) {
        RankingDTO createdRanking = rankingService.save(rankingDTO);
        return new ResponseEntity<>(createdRanking, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RankingRespDTO>> getAllRankings() {
        List<RankingRespDTO> rankings = rankingService.getAllRankings();
        return new ResponseEntity<>(rankings, HttpStatus.OK);
    }

    @GetMapping("/{memberId}/{competitionCode}")
    public ResponseEntity<RankingDTO> getRankingById(
            @PathVariable Member memberId, @PathVariable Competition competitionCode) {
        RankingId rankingId = new RankingId(memberId, competitionCode);
        RankingDTO ranking = rankingService.getRankingById(rankingId);
        return ranking != null
                ? new ResponseEntity<>(ranking, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{memberId}/{competitionCode}")
    public ResponseEntity<RankingDTO> updateRanking(
            @PathVariable Member memberId,
            @PathVariable Competition competitionCode,
            @Valid @RequestBody RankingDTO rankingDTO) {
        RankingId rankingId = new RankingId(memberId, competitionCode);
        RankingDTO updatedRanking = rankingService.updateRanking(rankingId, rankingDTO);
        return updatedRanking != null
                ? new ResponseEntity<>(updatedRanking, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{memberId}/{competitionCode}")
    public ResponseEntity<String> deleteRanking(
            @PathVariable Member memberId, @PathVariable Competition competitionCode) {
        try {
            RankingId rankingId = new RankingId(memberId, competitionCode);
            boolean deleted = rankingService.deleteRanking(rankingId);
            return deleted
                    ? new ResponseEntity<>("Ranking deleted successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Ranking not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
