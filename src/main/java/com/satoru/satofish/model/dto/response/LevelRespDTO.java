package com.satoru.satofish.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LevelRespDTO
{
    @NotNull(message = "Level code cannot be null")
    private Long code;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Positive(message = "Points must be positive")
    private Integer points;

}
