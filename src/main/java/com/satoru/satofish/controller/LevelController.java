package com.satoru.satofish.controller;

import com.satoru.satofish.model.dto.LevelDTO;
import com.satoru.satofish.model.dto.response.LevelRespDTO;
import com.satoru.satofish.service.HuntingService;
import com.satoru.satofish.service.LevelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/levels")
@CrossOrigin
public class LevelController {

    @Autowired
    private LevelService levelService;


    @PostMapping("/add")
    public ResponseEntity<Object> createLevel(@Valid @RequestBody LevelDTO levelDTO) {
        try {
            LevelDTO createdLevel = levelService.save(levelDTO);
            return new ResponseEntity<>(createdLevel, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: Unable to create the level.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<LevelRespDTO>> getAllLevels() {
        List<LevelRespDTO> levels = levelService.getAllLevels();
        return new ResponseEntity<>(levels, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<LevelRespDTO> getLevelById(@PathVariable Long code) {
        LevelRespDTO level = levelService.getLevelById(code);
        return level != null
                ? new ResponseEntity<>(level, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{code}")
    public ResponseEntity<LevelDTO> updateLevel(
            @PathVariable Long code, @RequestBody LevelDTO levelDTO) {
        LevelDTO updatedLevel = levelService.updateLevel(code, levelDTO);
        return updatedLevel != null
                ? new ResponseEntity<>(updatedLevel, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteLevel(@PathVariable Long code) {
        try {
            boolean deleted = levelService.deleteLevel(code);
            return deleted
                    ? new ResponseEntity<>("Level deleted successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Level not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
