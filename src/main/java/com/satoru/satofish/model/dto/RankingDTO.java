package com.satoru.satofish.model.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingDTO {

    private Long memberNum;

    private String competitionCode ;

    @Positive(message = "Position must be positive")
    private Integer position;

    @Positive(message = "Score must be positive")
    private Integer score;



}
