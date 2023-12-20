package com.satoru.satofish.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Levels")

public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(name = "description")
    private String description;

    @Column(name = "points")
    private Integer points;

    @OneToMany(mappedBy = "level" ,fetch = FetchType.LAZY)
    private List<Fish> fishs;

}
