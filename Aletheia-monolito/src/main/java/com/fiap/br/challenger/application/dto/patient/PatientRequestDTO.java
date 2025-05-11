package com.fiap.br.challenger.application.dto.patient;

import com.fiap.br.challenger.domain.model.enums.Gender;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank(message = "Name is required.")
        String name,

        @NotNull(message = "Birthday is required.")
        LocalDate birthday,

        @NotNull(message = "Gender is required.")
        Gender gender,

        @NotNull(message = "Risk status is required.")
        RiskStatus riskStatus,

        @Positive(message = "Consultation frequency must be a positive number.")
        Integer consultationFrequency,

        @NotBlank(message = "Associated claims are required.")
        String associatedClaims


) { }
