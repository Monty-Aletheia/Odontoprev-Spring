package com.fiap.br.challenger.application.dto.consultation;

import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationResponseDTO {
    private UUID id;
    private LocalDate consultationDate;
    private Double consultationValue;
    private RiskStatus riskStatus;
    private String description;
    private PatientResponseDTO patient;
    private DentistResponseDTO dentist;
}
