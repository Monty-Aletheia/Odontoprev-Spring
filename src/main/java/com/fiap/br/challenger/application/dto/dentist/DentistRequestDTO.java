package com.fiap.br.challenger.application.dto.dentist;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DentistRequestDTO(
        @NotBlank(message = "Name is required.")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
        String name,

        @NotBlank(message = "Specialty is required.")
        @Size(min = 2, max = 100, message = "Specialty must be between 2 and 100 characters.")
        String specialty,

        @NotBlank(message = "Registration number is required.")
        @Size(min = 1, max = 20, message = "Registration number must be between 1 and 20 characters.")
        String registrationNumber,

        @NotBlank(message = "Password is required.")
        String password
) {}
