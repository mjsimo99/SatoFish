package com.satoru.satofish.service;

import com.satoru.satofish.model.dto.HuntingDTO;
import com.satoru.satofish.model.dto.response.HuntingRespDTO;
import com.satoru.satofish.model.entity.*;
import com.satoru.satofish.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class HuntingService {




    @Autowired
    private HuntingRepository huntingRepository;



    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FishRepository fishRepository;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private ModelMapper modelMapper;






    public HuntingDTO save(HuntingDTO huntingDTO) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        String competitionCode = huntingDTO.getCompetitionCode();

        //CompetitionRespDTO competition = competitionService.getCompetitionById(competitionCode);
        Competition competition = competitionRepository.findById(competitionCode).orElse(null);


        if (competition == null || !competition.getDate().isEqual(currentDate)) {
            throw new IllegalArgumentException("Hunting can only be added for today's competition");
        }

        if (currentTime.isAfter(competition.getEndTime())) {
            throw new IllegalArgumentException("Hunting cannot be added after the competition has ended");
        }

        if (currentTime.isBefore(competition.getStartTime())) {
            throw new IllegalArgumentException("Hunting cannot be added before the competition has started");
        }

        Long memberNum = huntingDTO.getMemberNum();
        String fishName = huntingDTO.getFishName();

        Optional<Hunting> existingHunting = huntingRepository.findByMemberNumAndCompetitionCodeAndFishName(
                memberNum, competitionCode, fishName);

        if (existingHunting.isPresent()) {
            Hunting hunting = existingHunting.get();
            hunting.setNumberOfFish(hunting.getNumberOfFish() + huntingDTO.getNumberOfFish());
            hunting = huntingRepository.save(hunting);

            Optional<Member> optionalMember = memberRepository.findById(memberNum);
            optionalMember.ifPresent(this::updateMemberScore);

            return modelMapper.map(hunting, HuntingDTO.class);
        } else {
            Hunting hunting = modelMapper.map(huntingDTO, Hunting.class);

            Fish fish = fishRepository.findByName(fishName)
                    .orElseGet(() -> {
                        Fish newFish = new Fish();
                        newFish.setName(fishName);
                        return fishRepository.save(newFish);
                    });

            hunting.setFish(fish);

            if (memberNum != null) {
                Member existingMember = memberRepository.findById(memberNum).orElse(null);
                if (existingMember != null) {
                    hunting.setMember(existingMember);
                }
            }

            if (competitionCode != null) {
                Competition existingCompetition = competitionRepository.findById(competitionCode).orElse(null);
                if (existingCompetition != null) {
                    hunting.setCompetition(existingCompetition);
                }
            }

            hunting = huntingRepository.save(hunting);

            assert memberNum != null;
            Optional<Member> optionalMember = memberRepository.findById(memberNum);
            optionalMember.ifPresent(this::updateMemberScore);

            return modelMapper.map(hunting, HuntingDTO.class);
        }
    }


    public List<HuntingRespDTO> getAllHuntings() {
        List<Hunting> huntingList = huntingRepository.findAll();
        return huntingList.stream()
                .map(hunting -> modelMapper.map(hunting, HuntingRespDTO.class))
                .collect(Collectors.toList());
    }

    public HuntingRespDTO getHuntingById(Long id) {
        Hunting hunting = huntingRepository.findById(id).orElse(null);
        return (hunting != null) ? modelMapper.map(hunting, HuntingRespDTO.class) : null;
    }

    public HuntingDTO updateHunting(Long id, HuntingDTO huntingDTO) {
        Hunting hunting = huntingRepository.findById(id).orElse(null);

        if (hunting != null) {
            hunting.setNumberOfFish(huntingDTO.getNumberOfFish());

            if ( huntingDTO.getMemberNum() != null) {
                Member member = memberRepository.findById(huntingDTO.getMemberNum()).orElse(null);

                if (member != null) {
                    hunting.setMember(member);
                }
            }

            if (huntingDTO.getFishName() != null) {
                Fish fish = fishRepository.findById(huntingDTO.getFishName()).orElse(null);

                if (fish != null) {
                    hunting.setFish(fish);
                }
            }

            if (huntingDTO.getCompetitionCode() != null) {
                Competition competition = competitionRepository.findById(huntingDTO.getCompetitionCode()).orElse(null);

                if (competition != null) {
                    hunting.setCompetition(competition);
                }
            }

            hunting = huntingRepository.save(hunting);
            return modelMapper.map(hunting, HuntingDTO.class);
        }
        return null;
    }

    public boolean deleteHunting(Long id) {
        Hunting hunting = huntingRepository.findById(id).orElse(null);
        if (hunting != null) {
            huntingRepository.delete(hunting);
            return true;
        }
        return false;
    }
    private void updateMemberScore(Member member) {
        List<Ranking> rankings = member.getRankings();

        for (Ranking ranking : rankings) {
            int newScore = calculateMemberScore(member.getHuntings(), ranking.getCompetition());
            ranking.setScore(newScore);
            rankingRepository.save(ranking);
        }
    }

    private int calculateMemberScore(List<Hunting> huntings, Competition competition) {
        int totalScore = 0;

        for (Hunting hunting : huntings) {
            if (hunting.getCompetition().equals(competition)) {
                Fish fish = hunting.getFish();
                if (fish != null) {
                    Level level = fish.getLevel();
                    int levelPoints = (level != null) ? level.getPoints() : 0;

                    int numberOfFish = hunting.getNumberOfFish();
                    totalScore += levelPoints * numberOfFish;
                }
            }
        }

        return totalScore;
    }








}
