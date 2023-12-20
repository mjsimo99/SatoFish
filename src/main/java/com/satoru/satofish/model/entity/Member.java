package com.satoru.satofish.model.entity;

import com.satoru.satofish.model.enumeration.IdentityDocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Member ID cannot be null")
    private Long num;

    @Column(name = "name")
    private String name;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "accession_date")
    private LocalDate accessionDate;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "identity_document")
    private IdentityDocumentType identityDocument;

    @Column(name = "identity_number", unique = true, nullable = false)
    private String identityNumber;


    @OneToMany(mappedBy = "id.member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ranking> rankings;

    @OneToMany(mappedBy = "member" ,fetch = FetchType.LAZY)
    private List<Hunting> huntings;
}
