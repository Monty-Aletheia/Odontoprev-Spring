package com.fiap.br.challenger.application.dto.consultation;

import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationResponseDTO extends RepresentationModel<ConsultationResponseDTO> {
    private UUID id;
    private LocalDate consultationDate;
    private Double consultationValue;
    private RiskStatus riskStatus;
    private String description;
    private PatientResponseDTO patient;
    private List<DentistResponseDTO> dentists;
}
