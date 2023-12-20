package com.satoru.satofish.service;

import com.satoru.satofish.model.dto.LevelDTO;
import com.satoru.satofish.model.dto.response.LevelRespDTO;
import com.satoru.satofish.model.entity.Level;
import com.satoru.satofish.repository.LevelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private ModelMapper modelMapper;



    public LevelDTO save(LevelDTO levelDTO) {
        if (isNewLevelValid(levelDTO.getPoints())) {
            Level level = modelMapper.map(levelDTO, Level.class);
            level.setPoints(levelDTO.getPoints());
            level = levelRepository.save(level);
            return modelMapper.map(level, LevelDTO.class);
        } else {
            throw new RuntimeException("New level points must be higher than the highest level points");
        }
    }

    private boolean isNewLevelValid(Integer newPoints) {
        Integer highestPoints = levelRepository.findHighestPoints();

        return highestPoints == null || newPoints > highestPoints;
    }

    public List<LevelRespDTO> getAllLevels() {
        List<Level> levels = levelRepository.findAll();
        return levels.stream()
                .map(level -> modelMapper.map(level, LevelRespDTO.class))
                .collect(Collectors.toList());
    }

    public LevelRespDTO getLevelById(Long code) {
        Level level = levelRepository.findById(code)
                .orElse(null);
        return (level != null) ? modelMapper.map(level, LevelRespDTO.class) : null;
    }

    public LevelDTO updateLevel(Long code, LevelDTO levelDTO) {
        Level level = levelRepository.findById(code)
                .orElse(null);
        if (level != null) {
            level = modelMapper.map(levelDTO, Level.class);
            level.setCode(code);
            level = levelRepository.save(level);
            return modelMapper.map(level, LevelDTO.class);
        }
        return null;
    }

    public boolean deleteLevel(Long code) {
        Level level = levelRepository.findById(code)
                .orElse(null);
        if (level != null) {
            levelRepository.delete(level);
            return true;
        }
        return false;
    }
}
