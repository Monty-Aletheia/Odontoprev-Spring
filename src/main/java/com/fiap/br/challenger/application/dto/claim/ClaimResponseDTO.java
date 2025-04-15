package com.fiap.br.challenger.application.dto.claim;

import com.fiap.br.challenger.domain.model.enums.ClaimType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponseDTO {
    private UUID id;
    private LocalDate occurrenceDate;
    private Double value;
    private ClaimType claimType;
    private String suggestedPreventiveAction;
    private UUID consultationId;
}
