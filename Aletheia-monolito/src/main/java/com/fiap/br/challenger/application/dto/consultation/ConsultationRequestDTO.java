package com.fiap.br.challenger.application.dto.consultation;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ConsultationRequestDTO(
        @NotNull(message = "Consultation date is required.")
        LocalDate date,

        @NotNull(message = "Consultation value is required.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Consultation value must be greater than zero.")
        @DecimalMax(value = "10000.0", message = "Consultation value must be less than or equal to 10,000.")
        Double value,

        @NotNull(message = "Patient ID is required.")
        UUID patient,

        @NotNull(message = "Dentist ID  is required.")
        UUID dentist
) {}
