package com.satoru.satofish.model.dto.response;

import com.satoru.satofish.model.dto.CompetitionDTO;
import com.satoru.satofish.model.dto.MemberDTO;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingRespDTO {

        private MemberDTO member;

        private CompetitionDTO competition;

        @Positive(message = "Position must be positive")
        private Integer position;

        @Positive(message = "Score must be positive")
        private Integer score;



}