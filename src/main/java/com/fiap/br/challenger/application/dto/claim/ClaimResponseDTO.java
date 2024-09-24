package com.fiap.br.challenger.application.dto.claim;

import com.fiap.br.challenger.domain.model.enums.ClaimType;

import java.time.LocalDate;
import java.util.UUID;

public record ClaimResponseDTO(
        UUID id,
        LocalDate occurrenceDate,
        Double value,
        ClaimType claimType,
        String suggestedPreventiveAction,
        UUID consultationId) {
}
