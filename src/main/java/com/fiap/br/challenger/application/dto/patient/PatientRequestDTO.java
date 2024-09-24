package com.fiap.br.challenger.application.dto.patient;

import com.fiap.br.challenger.domain.model.enums.Gender;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotNull(message = "A data de nascimento é obrigatória")
        LocalDate birthday,

        @NotNull(message = "O gênero é obrigatório")
        Gender gender,

        @NotNull(message = "O status de risco é obrigatório")
        RiskStatus riskStatus,

        @Positive(message = "A frequência de consulta deve ser um número positivo")
        Integer consultationFrequency,

        @NotBlank(message = "As reclamações associadas são obrigatórias")
        String associatedClaims
) { }
