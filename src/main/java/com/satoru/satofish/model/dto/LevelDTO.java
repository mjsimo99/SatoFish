package com.satoru.satofish.model.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LevelDTO {

    @NotNull(message = "Level code cannot be null")
    private Long code;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Positive(message = "Points must be positive")
    private Integer points;

}
