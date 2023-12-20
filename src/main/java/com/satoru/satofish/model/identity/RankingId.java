package com.satoru.satofish.model.identity;

import com.satoru.satofish.model.entity.Competition;
import com.satoru.satofish.model.entity.Member;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RankingId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "competition_code")
    private Competition competition;
}
