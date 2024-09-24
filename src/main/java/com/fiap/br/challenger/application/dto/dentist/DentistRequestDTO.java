package com.fiap.br.challenger.application.dto.dentist;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DentistRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String name,

        @NotBlank(message = "A especialidade é obrigatória")
        @Size(min = 2, max = 100, message = "A especialidade deve ter entre 2 e 100 caracteres")
        String specialty,

        @NotBlank(message = "O número de registro é obrigatório")
        @Size(min = 1, max = 20, message = "O número de registro deve ter entre 1 e 20 caracteres")
        String registrationNumber,

        @NotNull(message = "A taxa de reclamações é obrigatória")
        @DecimalMin(value = "0.0", inclusive = false, message = "A taxa de reclamações deve ser maior que 0")
        Double claimsRate,

        @NotNull(message = "O status de risco é obrigatório")
        RiskStatus riskStatus
) {}
