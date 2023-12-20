package com.satoru.satofish.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Fishs")
public class Fish {

    @Id
    private String name;

    @Column(name = "average_weight")
    private double averageWeight;

    @OneToMany(mappedBy = "fish", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Hunting> huntings;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Level level;
}
