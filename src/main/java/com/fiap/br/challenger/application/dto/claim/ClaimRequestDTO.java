package com.fiap.br.challenger.application.dto.claim;

import com.fiap.br.challenger.domain.model.enums.ClaimType;

import java.time.LocalDate;
import java.util.UUID;

public record ClaimRequestDTO(
        LocalDate occurrenceDate,
        Double value,
        ClaimType claimType,
        String suggestedPreventiveAction,
        UUID consultationId) {

}
