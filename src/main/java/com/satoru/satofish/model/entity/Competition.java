package com.satoru.satofish.model.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Competitions")

public class Competition {

    @Id
    private String code;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "number_of_participants")
    private int numberOfParticipants;

    @Column(name = "location")
    private String location;

    @Column(name = "amount")
    private double amount;

    @OneToMany(mappedBy = "competition",  cascade = CascadeType.ALL)
    private List<Ranking> rankings;

    @OneToMany(mappedBy = "competition" , cascade = CascadeType.ALL)
    private List<Hunting> huntings;
}