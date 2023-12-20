package com.satoru.satofish.model.dto.response;


import com.satoru.satofish.model.dto.HuntingDTO;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionRespDTO {

    @NotBlank(message = "Code cannot be blank")
    private String code;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;

    @Min(value = 1, message = "Number of participants must be at least 1")
    private int numberOfParticipants;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Positive(message = "Amount must be positive")
    private double amount;

    private List<RankingCompRespDTO> rankings ;

    private List<HuntingDTO> huntings ;
}
