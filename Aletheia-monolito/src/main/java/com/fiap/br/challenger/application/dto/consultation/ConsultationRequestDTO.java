package com.fiap.br.challenger.application.dto.consultation;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ConsultationRequestDTO(
        @NotNull(message = "Consultation date is required.")
        LocalDate consultationDate,

        @NotNull(message = "Consultation value is required.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Consultation value must be greater than zero.")
        @DecimalMax(value = "10000.0", message = "Consultation value must be less than or equal to 10,000.")
        Double consultationValue,

        @NotNull(message = "Risk status is required.")
        RiskStatus riskStatus,

        @NotNull(message = "Patient ID is required.")
        UUID patientId,

        @NotNull(message = "List of dentists is required.")
        @Size(min = 1, message = "At least one dentist ID must be provided.")
        List<UUID> dentistIds,

        @Size(max = 500, message = "Description cannot exceed 500 characters.")
        String description
) {}
