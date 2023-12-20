package com.satoru.satofish.model.dto.response;

import com.satoru.satofish.model.dto.HuntingDTO;
import com.satoru.satofish.model.dto.LevelDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FishRespDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Positive(message = "Average weight must be positive")
    private double averageWeight;

    private LevelDTO level;

    private List<HuntingDTO> huntings;


}

