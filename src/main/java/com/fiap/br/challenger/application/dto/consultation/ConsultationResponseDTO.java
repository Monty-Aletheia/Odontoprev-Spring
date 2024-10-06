package com.fiap.br.challenger.application.dto.consultation;

import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record ConsultationResponseDTO(
        UUID id,
        Date date,
        Double consultationValue,
        RiskStatus riskStatus,
        String description,
        PatientResponseDTO patient,
        List<DentistResponseDTO> dentists
) {
}
