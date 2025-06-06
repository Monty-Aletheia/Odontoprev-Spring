package com.fiap.br.challenger.application.dto.dentist;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DentistResponseDTO  {
    private UUID id;
    private String name;
    private String specialty;
    private String registrationNumber;
    private Double claimsRate;
    private RiskStatus riskStatus;
}
