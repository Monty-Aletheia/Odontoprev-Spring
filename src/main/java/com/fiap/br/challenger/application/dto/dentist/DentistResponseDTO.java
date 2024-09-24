package com.fiap.br.challenger.application.dto.dentist;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;

import java.util.UUID;

public record DentistResponseDTO(
        UUID id,
        String name,
        String specialty,
        String registrationNumber,
        Double claimsRate,
        RiskStatus riskStatus) {}
