package com.fiap.br.challenger.application.dto.claim;

import com.fiap.br.challenger.domain.model.enums.ClaimType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record ClaimRequestDTO(
        @NotNull(message = "Occurrence date cannot be null")
        LocalDate occurrenceDate,

        @NotNull(message = "Value cannot be null")
        @Positive(message = "Value must be positive")
        Double value,

        @NotNull(message = "Claim type cannot be null")
        ClaimType claimType,

        @Size(max = 255, message = "Suggested preventive action cannot exceed 255 characters")
        String suggestedPreventiveAction,

        @NotNull(message = "Consultation ID cannot be null")
        UUID consultationId) {
}
