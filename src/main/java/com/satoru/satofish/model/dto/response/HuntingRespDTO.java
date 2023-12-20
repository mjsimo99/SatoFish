package com.satoru.satofish.model.dto.response;

import com.satoru.satofish.model.dto.CompetitionDTO;
import com.satoru.satofish.model.dto.FishDTO;
import com.satoru.satofish.model.dto.MemberDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HuntingRespDTO {
    @NotNull(message = "ID cannot be null")
    private Long id;

    @Positive(message = "Number of fish must be positive")
    private Integer numberOfFish;

    private CompetitionDTO competition;

    private MemberDTO member;

    private FishDTO fish;


}
