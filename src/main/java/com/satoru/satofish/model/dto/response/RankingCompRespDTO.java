package com.satoru.satofish.model.dto.response;

import com.satoru.satofish.model.dto.MemberDTO;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingCompRespDTO {

    private MemberDTO member;

    @Positive(message = "Position must be positive")
    private Integer position;

    @Positive(message = "Score must be positive")
    private Integer score;



}
