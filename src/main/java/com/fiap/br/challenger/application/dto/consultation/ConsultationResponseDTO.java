package com.fiap.br.challenger.application.dto.consultation;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;

import java.util.Date;
import java.util.UUID;

public record ConsultationResponseDTO(
        UUID id,
        Date date,
        Double consultationValue,
        RiskStatus riskStatus,
        String description
) {
}
