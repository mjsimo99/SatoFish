package com.satoru.satofish.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionDTO {

    @NotBlank(message = "Code cannot be blank")
    @Pattern(regexp = "[A-Za-z]{3}-(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{2}", message = "Code should follow the pattern 'XXX-DD-MM-YY'")
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





}
