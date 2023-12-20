package com.satoru.satofish.model.dto;

import com.satoru.satofish.model.enumeration.IdentityDocumentType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @NotNull(message = "Member ID cannot be null")
    private Long num;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Family name cannot be blank")
    private String familyName;

    @Past(message = "Accession date must be in the past")
    private LocalDate accessionDate;

    @NotBlank(message = "Nationality cannot be blank")
    private String nationality;

    @NotNull(message = "Identity document type cannot be null")
    private IdentityDocumentType identityDocument;

    @NotBlank(message = "Identity number cannot be blank")
    private String identityNumber;



}
