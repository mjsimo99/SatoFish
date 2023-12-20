package com.satoru.satofish.controller;



import com.satoru.satofish.model.dto.CompetitionDTO;
import com.satoru.satofish.model.dto.response.CompetitionRespDTO;
import com.satoru.satofish.service.CompetitionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/competitions")
@CrossOrigin
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;



    @PostMapping("/add")
    public ResponseEntity<CompetitionDTO> createCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) {
        CompetitionDTO createdCompetition = competitionService.save(competitionDTO);
        return new ResponseEntity<>(createdCompetition, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<CompetitionRespDTO>> getAllCompetitions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        Page<CompetitionRespDTO> competitions = competitionService.getAllCompetitions(page, size);
        return ResponseEntity.ok(competitions);
    }


    @GetMapping("/{code}")
    public ResponseEntity<CompetitionRespDTO> getCompetitionById(@PathVariable String code) {
        CompetitionRespDTO competition = competitionService.getCompetitionById(code);
        return competition != null
                ? new ResponseEntity<>(competition, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{code}")
    public ResponseEntity<CompetitionDTO> updateCompetition(
            @PathVariable String code, @Valid @RequestBody CompetitionDTO competitionDTO) {
        CompetitionDTO updatedCompetition = competitionService.updateCompetition(code, competitionDTO);
        return updatedCompetition != null
                ? new ResponseEntity<>(updatedCompetition, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteCompetition(@PathVariable String code) {
        try {
            boolean deleted = competitionService.deleteCompetition(code);
            return deleted
                    ? new ResponseEntity<>("Competition deleted successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Competition not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
