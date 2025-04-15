package com.fiap.br.challenger.application.dto.patient;

import com.fiap.br.challenger.domain.model.enums.Gender;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO  {
    private UUID id;
    private String name;
    private LocalDate birthday;
    private Gender gender;
    private RiskStatus riskStatus;
    private Integer consultationFrequency;
    private String associatedClaims;
}

