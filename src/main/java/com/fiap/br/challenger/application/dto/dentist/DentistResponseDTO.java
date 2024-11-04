package com.fiap.br.challenger.application.dto.dentist;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DentistResponseDTO extends RepresentationModel<DentistResponseDTO> {
    private UUID id;
    private String name;
    private String specialty;
    private String registrationNumber;
    private Double claimsRate;
    private RiskStatus riskStatus;
}
