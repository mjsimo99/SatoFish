// HuntingController.java
package com.satoru.satofish.controller;

import com.satoru.satofish.model.dto.CompetitionDTO;
import com.satoru.satofish.model.dto.HuntingDTO;
import com.satoru.satofish.model.dto.response.CompetitionRespDTO;
import com.satoru.satofish.model.dto.response.HuntingRespDTO;
import com.satoru.satofish.service.CompetitionService;
import com.satoru.satofish.service.HuntingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/hunting")
@CrossOrigin
@Validated
public class HuntingController {

    @Autowired
    private HuntingService huntingService;


    @PostMapping("/add")
    public ResponseEntity<HuntingDTO> createHunting(@Valid @RequestBody HuntingDTO huntingDTO) {
        HuntingDTO createdHunting = huntingService.save(huntingDTO);
        return new ResponseEntity<>(createdHunting, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<HuntingRespDTO>> getAllHuntings() {
        List<HuntingRespDTO> huntingList = huntingService.getAllHuntings();
        return new ResponseEntity<>(huntingList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<HuntingRespDTO> getHuntingById(@PathVariable Long id) {
        HuntingRespDTO hunting = huntingService.getHuntingById(id);
        return hunting != null
                ? new ResponseEntity<>(hunting, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HuntingDTO> updateHunting(
            @PathVariable Long id, @Valid @RequestBody HuntingDTO huntingDTO) {
        HuntingDTO updatedHunting = huntingService.updateHunting(id, huntingDTO);
        return updatedHunting != null
                ? new ResponseEntity<>(updatedHunting, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHunting(@PathVariable Long id) {
        try {
            boolean deleted = huntingService.deleteHunting(id);
            return deleted
                    ? new ResponseEntity<>("Hunting deleted successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Hunting not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
