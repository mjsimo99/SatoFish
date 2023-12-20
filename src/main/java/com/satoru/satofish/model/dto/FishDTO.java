package com.satoru.satofish.model.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FishDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Positive(message = "Average weight must be positive")
    private double averageWeight;

    private Long levelCode;


}
