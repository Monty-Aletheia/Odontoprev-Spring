package com.fiap.br.challenger.application.dto.patient;

import com.fiap.br.challenger.domain.model.enums.Gender;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;

import java.time.LocalDate;

public record PatientResponseDTO(
        String name,
        LocalDate birthday,
        Gender gender,
        RiskStatus riskStatus,
        Integer consultationFrequency,
        String associatedClaims
) {}
