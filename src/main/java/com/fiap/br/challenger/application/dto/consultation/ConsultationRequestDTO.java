package com.fiap.br.challenger.application.dto.consultation;

import com.fiap.br.challenger.application.dto.claim.ClaimResponseDTO;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record ConsultationRequestDTO(
        @NotNull(message = "Consultation date is required.")
        Date consultationDate,

        @NotNull(message = "Consultation value is required.")
        Double consultationValue,

        @NotNull(message = "Risk status is required.")
        RiskStatus riskStatus,

        @NotNull(message = "Patient ID is required.")
        UUID patientId,

        @NotNull(message = "List of dentists is required.")
        List<UUID> dentistIds,

        String description

){}