package com.satoru.satofish.service;

import com.satoru.satofish.exception.NotFoundException;
import com.satoru.satofish.model.dto.FishDTO;
import com.satoru.satofish.model.entity.Fish;
import com.satoru.satofish.model.entity.Level;
import com.satoru.satofish.model.dto.response.FishRespDTO;
import com.satoru.satofish.repository.FishRepository;
import com.satoru.satofish.repository.LevelRepository;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FishService {

    @Autowired
    private FishRepository fishRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LevelRepository levelRepository;


    public FishDTO save(FishDTO fishDTO) {
        Fish fish = modelMapper.map(fishDTO, Fish.class);

        if (fishDTO.getLevelCode() != null) {
            Level existingLevel = levelRepository.findById(fishDTO.getLevelCode()).orElse(null);

            if (existingLevel != null) {
                fish.setLevel(existingLevel);
            }
        }else {
            throw new NotFoundException("level not found");
        }

        fish = fishRepository.save(fish);
        return modelMapper.map(fish, FishDTO.class);
    }




    public List<FishRespDTO> getAllFish() {
        List<Fish> fishList = fishRepository.findAll();
        return fishList.stream()
                .map(fish -> modelMapper.map(fish, FishRespDTO.class))
                .collect(Collectors.toList());
    }

    public FishRespDTO getFishByName(String name) {
        Fish fish = fishRepository.findById(name).orElse(null);
        return (fish != null) ? modelMapper.map(fish, FishRespDTO.class) : null;
    }

    public FishDTO updateFish(String name, FishDTO fishDTO) {
        Fish fish = fishRepository.findById(name).orElse(null);

        if (fish != null) {
            fish.setAverageWeight(fishDTO.getAverageWeight());

            if (fishDTO.getLevelCode() != null) {
                Level level = levelRepository.findById(fishDTO.getLevelCode()).orElse(null);

                if (level != null) {
                    fish.setLevel(level);
                }
            }

            fish = fishRepository.save(fish);
            return modelMapper.map(fish, FishDTO.class);
        }

        return null;
    }


    public boolean deleteFish(String name) {
        Fish fish = fishRepository.findById(name).orElse(null);
        if (fish != null) {
            fishRepository.delete(fish);
            return true;
        }
        return false;
    }
}
