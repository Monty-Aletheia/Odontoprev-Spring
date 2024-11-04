package com.fiap.br.challenger.application.dto.claim;

import com.fiap.br.challenger.domain.model.enums.ClaimType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponseDTO extends RepresentationModel<ClaimResponseDTO> {
    private UUID id;
    private LocalDate occurrenceDate;
    private Double value;
    private ClaimType claimType;
    private String suggestedPreventiveAction;
    private UUID consultationId;
}
