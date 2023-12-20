package com.satoru.satofish.controller;

import com.satoru.satofish.model.dto.FishDTO;
import com.satoru.satofish.model.dto.response.FishRespDTO;
import com.satoru.satofish.service.FishService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fish")
@CrossOrigin
public class FishController {

    @Autowired
    private FishService fishService;

    @PostMapping("/add")
    public ResponseEntity<FishDTO> createFish(@Valid @RequestBody FishDTO fishDTO) {
        FishDTO createdFish = fishService.save(fishDTO);
        return new ResponseEntity<>(createdFish, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FishRespDTO>> getAllFish() {
        List<FishRespDTO> fishList = fishService.getAllFish();
        return new ResponseEntity<>(fishList, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<FishRespDTO> getFishByName(@PathVariable String name) {
        FishRespDTO fish = fishService.getFishByName(name);
        return fish != null
                ? new ResponseEntity<>(fish, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{name}")
    public ResponseEntity<FishDTO> updateFish(
            @PathVariable String name, @Valid @RequestBody FishDTO fishDTO) {
        FishDTO updatedFish = fishService.updateFish(name, fishDTO);
        return updatedFish != null
                ? new ResponseEntity<>(updatedFish, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteFish(@PathVariable String name) {
        try {
            boolean deleted = fishService.deleteFish(name);
            return deleted
                    ? new ResponseEntity<>("Fish deleted successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Fish not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
