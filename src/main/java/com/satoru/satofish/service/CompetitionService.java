package com.satoru.satofish.service;

import com.satoru.satofish.model.dto.CompetitionDTO;
import com.satoru.satofish.model.entity.Competition;
import com.satoru.satofish.model.dto.response.CompetitionRespDTO;
import com.satoru.satofish.repository.CompetitionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionService {
    private static final int LOCATION_CODE_LENGTH = 3;
    private static final String CODE_PATTERN = "%s-%s";

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private ModelMapper modelMapper;


    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        String generatedCode = generateCompetitionCode(competitionDTO.getLocation(), competitionDTO.getDate());

        competitionDTO.setCode(generatedCode);
        LocalTime startTime = competitionDTO.getStartTime();
        LocalTime endTime = competitionDTO.getEndTime();
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }


        Competition competition = modelMapper.map(competitionDTO, Competition.class);
        competition = competitionRepository.save(competition);

        return modelMapper.map(competition, CompetitionDTO.class);
    }

    private String generateCompetitionCode(String location, LocalDate date) {
        String locationCode = location != null ? location.substring(0, Math.min(LOCATION_CODE_LENGTH, location.length())).toLowerCase() : "";
        String dateCode = date != null ? date.format(DateTimeFormatter.ofPattern("yy-MM-dd")) : "";
        return String.format(CODE_PATTERN, locationCode, dateCode);
    }


    public Page<CompetitionRespDTO> getAllCompetitions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Competition> competitions = competitionRepository.findAll(pageable);
        return competitions.map(competition -> modelMapper.map(competition, CompetitionRespDTO.class));
    }




    public CompetitionRespDTO getCompetitionById(String code) {
        Competition competition = competitionRepository.findById(code)
                .orElse(null);
        return (competition != null) ? modelMapper.map(competition, CompetitionRespDTO.class) : null;
    }
    public CompetitionDTO updateCompetition(String code, CompetitionDTO competitionDTO) {
        Competition competition = competitionRepository.findById(code)
                .orElse(null);
        if (competition != null) {
            competition = modelMapper.map(competitionDTO, Competition.class);
            competition.setCode(code);
            competition = competitionRepository.save(competition);
            return modelMapper.map(competition, CompetitionDTO.class);
        }
        return null;
    }
    public boolean deleteCompetition(String code) {
        Competition competition = competitionRepository.findById(code)
                .orElse(null);
        if (competition != null) {
            competitionRepository.delete(competition);
            return true;
        }
        return false;
    }


}
