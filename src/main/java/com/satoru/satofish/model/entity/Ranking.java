package com.satoru.satofish.model.entity;

import com.satoru.satofish.model.identity.RankingId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "Rankings")
@Data
@ToString
public class Ranking {

    @EmbeddedId
    private RankingId id;

    @Column(name = "position")
    private Integer position;

    @Column(name = "score")
    private Integer score;

    @MapsId("member")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("competition")
    @ManyToOne
    @JoinColumn(name = "competition_code")
    private Competition competition;
}
