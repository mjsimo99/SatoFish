package com.satoru.satofish.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HuntingDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @Positive(message = "Number of fish must be positive")
    private Integer numberOfFish;

    private String competitionCode;

    private Long memberNum;

    private  String fishName;


}
