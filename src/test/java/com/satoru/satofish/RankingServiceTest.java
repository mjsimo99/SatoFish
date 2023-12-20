package com.satoru.satofish;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.satoru.satofish.model.dto.MemberDTO;
import com.satoru.satofish.model.entity.Member;
import com.satoru.satofish.service.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;


import com.satoru.satofish.model.dto.response.RankingRespDTO;
import com.satoru.satofish.model.entity.Competition;
import com.satoru.satofish.model.entity.Ranking;
import com.satoru.satofish.repository.CompetitionRepository;
import com.satoru.satofish.repository.MemberRepository;
import com.satoru.satofish.repository.RankingRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RankingServiceTest {

    @InjectMocks
    private RankingService rankingService;

    @Mock
    private RankingRepository rankingRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CompetitionRepository competitionRepository;

    @BeforeEach

    void setUp() {



    }

    @Test
    void testCalculatePodium() {
        String competitionCode = "saf-2023-01-01";
        Competition competition = new Competition();
        when(competitionRepository.findById(competitionCode)).thenReturn(Optional.of(competition));

        List<Ranking> currentRankings = new ArrayList<>();
        when(rankingRepository.findByCompetitionOrderByScoreDesc(competition)).thenReturn(currentRankings);

        List<RankingRespDTO> podium = rankingService.calculatePodium(competitionCode);

        verify(competitionRepository).findById(competitionCode);
        verify(rankingRepository).findByCompetitionOrderByScoreDesc(competition);
        verify(rankingRepository, times(currentRankings.size())).save(any());

        assertNotNull(podium);
    }

    @Test
    void testUpdatePositionsInDatabase() {
        List<Ranking> currentRankings = new ArrayList<>();
        Ranking ranking1 = new Ranking();
        Ranking ranking2 = new Ranking();
        currentRankings.add(ranking1);
        currentRankings.add(ranking2);

        rankingService.updatePositionsInDatabase(currentRankings);

        verify(rankingRepository, times(currentRankings.size())).save(any());

    }



    @Test
    void testCalculatePodium_Successful() {
        String competitionCode = "ims-22-12-23";
        Competition competition = new Competition();
        competition.setCode(competitionCode);
        competition.setDate(LocalDate.now());
        competition.setStartTime(LocalTime.now());
        competition.setEndTime(LocalTime.of(23, 59, 59));
        competition.setNumberOfParticipants(3);
        competition.setLocation("Maroc");
        competition.setAmount(1000.0);

        List<Ranking> currentRankings = new ArrayList<>();
        currentRankings.add(new Ranking());
        currentRankings.add(new Ranking());
        currentRankings.add(new Ranking());

        when(competitionRepository.findById(competitionCode)).thenReturn(Optional.of(competition));
        when(rankingRepository.findByCompetitionOrderByScoreDesc(competition)).thenReturn(currentRankings);

        List<RankingRespDTO> podium = rankingService.calculatePodium(competitionCode);

        verify(competitionRepository).findById(competitionCode);
        verify(rankingRepository).findByCompetitionOrderByScoreDesc(competition);
        verify(rankingRepository, times(currentRankings.size())).save(any());

        assertNotNull(podium);
        assertEquals(3, podium.size());
    }

    @Test
    void testCalculatePodium_EmptyPodium() {
        String competitionCode = "ims-22-12-23";
        Competition competition = new Competition();
        competition.setCode(competitionCode);
        competition.setDate(LocalDate.now());
        competition.setStartTime(LocalTime.now());

        List<Ranking> currentRankings = new ArrayList<>();

        when(competitionRepository.findById(competitionCode)).thenReturn(Optional.of(competition));
        when(rankingRepository.findByCompetitionOrderByScoreDesc(competition)).thenReturn(currentRankings);

        List<RankingRespDTO> podium = rankingService.calculatePodium(competitionCode);

        verify(competitionRepository).findById(competitionCode);
        verify(rankingRepository).findByCompetitionOrderByScoreDesc(competition);

        assertNotNull(podium);
        assertTrue(podium.isEmpty());
    }

    @Test
    void testCalculatePodium_NoCompetitionFound() {
        String competitionCode = "non-existent-code";

        when(competitionRepository.findById(competitionCode)).thenReturn(Optional.empty());

        List<RankingRespDTO> podium = rankingService.calculatePodium(competitionCode);

        verify(competitionRepository).findById(competitionCode);

        assertNull(podium);
    }



}
