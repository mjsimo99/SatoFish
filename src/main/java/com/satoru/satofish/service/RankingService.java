package com.satoru.satofish.service;

import com.satoru.satofish.exception.FullException;
import com.satoru.satofish.exception.DataNotFoundException;
import com.satoru.satofish.model.dto.CompetitionDTO;
import com.satoru.satofish.model.dto.MemberDTO;
import com.satoru.satofish.model.dto.RankingDTO;
import com.satoru.satofish.model.dto.response.RankingRespDTO;
import com.satoru.satofish.model.entity.Competition;
import com.satoru.satofish.model.entity.Member;
import com.satoru.satofish.model.entity.Ranking;
import com.satoru.satofish.model.identity.RankingId;
import com.satoru.satofish.repository.CompetitionRepository;
import com.satoru.satofish.repository.MemberRepository;
import com.satoru.satofish.repository.RankingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CompetitionRepository competitionRepository;


    public List<RankingRespDTO> calculatePodium(String competitionCode) {
        try {
            Optional<Competition> competition = competitionRepository.findById(competitionCode);

            if (competition.isPresent()) {
                List<Ranking> currentRankings = rankingRepository.findByCompetitionOrderByScoreDesc(competition.get());

                List<RankingRespDTO> podium = calculatePodiumPositions(currentRankings);

                updatePositionsInDatabase(currentRankings);

                return podium;
            } else {
                System.out.println("Competition not found. Cannot calculate podium.");
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void updatePositionsInDatabase(List<Ranking> currentRankings) {
        int position = 1;
        for (Ranking ranking : currentRankings) {
            ranking.setPosition(position);
            rankingRepository.save(ranking);
            position++;
        }
    }


    private List<RankingRespDTO> calculatePodiumPositions(List<Ranking> currentRankings) {
        List<RankingRespDTO> podium = new ArrayList<>();

        int position = 1;
        for (Ranking ranking : currentRankings) {
            if (position <= 6) {
                podium.add(mapToRankingRespDTO(ranking, position));
            } else {
                break;
            }
            position++;
        }

        return podium;
    }



    private RankingRespDTO mapToRankingRespDTO(Ranking ranking, int position) {
        return new RankingRespDTO(
                modelMapper.map(ranking.getMember(), MemberDTO.class),
                modelMapper.map(ranking.getCompetition(), CompetitionDTO.class),
                position,
                ranking.getScore()
        );
    }


public RankingDTO save(RankingDTO rankingDTO) {
    try {
        Optional<Member> member = memberRepository.findById(rankingDTO.getMemberNum());
        Optional<Competition> competition = competitionRepository.findById(rankingDTO.getCompetitionCode());

        if (member.isPresent() && competition.isPresent() && isCompetitionOpenForRegistration(competition.get())) {
            long currentParticipants = rankingRepository.countParticipantsByCompetition(competition.get());

            if ((int) currentParticipants < competition.get().getNumberOfParticipants()) {

                Ranking ranking = modelMapper.map(rankingDTO, Ranking.class);
                ranking.setId(new RankingId(member.get(), competition.get()));
                ranking.setScore(0);
                ranking.setPosition(0);

                ranking = rankingRepository.save(ranking);
                competitionRepository.save(competition.get());

                return modelMapper.map(ranking, RankingDTO.class);
            } else {
                throw new FullException("Competition is full. Cannot add ranking.");
            }
        } else {
            throw new DataNotFoundException("competition is not open for registration.");
        }
    } catch (DataNotFoundException | FullException e) {
        throw new RuntimeException(e.getMessage(), e);
    } catch (Exception e) {
        throw new RuntimeException("An unexpected error occurred while processing the request.", e);
    }
}



    private RankingDTO handleFailure(String errorMessage) {
        System.out.println(errorMessage);
        return null;
    }

    private RankingDTO handleFailure(String errorMessage, Exception e) {
        e.printStackTrace();
        return handleFailure(errorMessage);
    }

    private boolean isCompetitionOpenForRegistration(Competition competition) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (currentDate.isBefore(competition.getDate().minusDays(1)) ||
                (currentDate.isEqual(competition.getDate().minusDays(1)) && currentTime.isBefore(competition.getStartTime()))) {
            return true;
        }

        return false;
    }






    public List<RankingRespDTO> getAllRankings() {
        List<Ranking> rankings = rankingRepository.findAll();
        return rankings.stream()
                .map(ranking -> modelMapper.map(ranking, RankingRespDTO.class))
                .collect(Collectors.toList());
    }

    public RankingDTO getRankingById(RankingId rankingId) {
        Ranking ranking = rankingRepository.findById(rankingId)
                .orElse(null);
        return (ranking != null) ? modelMapper.map(ranking, RankingDTO.class) : null;
    }

    public RankingDTO updateRanking(RankingId rankingId, RankingDTO rankingDTO) {
        Ranking ranking = rankingRepository.findById(rankingId)
                .orElse(null);
        if (ranking != null) {
            ranking = modelMapper.map(rankingDTO, Ranking.class);
            ranking.setId(rankingId);
            ranking = rankingRepository.save(ranking);
            return modelMapper.map(ranking, RankingDTO.class);
        }
        return null;
    }

    public boolean deleteRanking(RankingId rankingId) {
        Ranking ranking = rankingRepository.findById(rankingId)
                .orElse(null);
        if (ranking != null) {
            rankingRepository.delete(ranking);
            return true;
        }
        return false;
    }
}
