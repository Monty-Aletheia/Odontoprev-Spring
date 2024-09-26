package com.fiap.br.challenger.application.dto.consultation;

import com.fiap.br.challenger.application.dto.claim.ClaimResponseDTO;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record ConsultationRequestDTO(
        @NotNull
        Date date,
        @NotNull
        Double consultationValue,
        @NotNull
        RiskStatus riskStatus,
        @NotNull
        UUID patientId,
        @NotNull
        List<UUID> dentistIds,

        String description,

        Set<ClaimResponseDTO> claims) {
}
