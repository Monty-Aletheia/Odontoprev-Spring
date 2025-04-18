package com.fiap.br.challenger.application.dto.patient;
import com.fiap.br.challenger.domain.model.enums.PlanType;
import com.fiap.br.challenger.domain.model.enums.SystemicDiseases;
import jakarta.validation.constraints.*;

public record PatientRiskAssessmentDTO(
        @Min(0) int age,
        @NotNull String gender,
        @Min(0) int consultationFrequency,
        @Min(0) int cavitiesHistory,
        @Min(0) int periodontalDisease,
        @Min(0) int numberOfImplants,
        @Min(0) int previousComplexTreatments,
        @Min(0) boolean smoker,
        @Min(0) boolean alcoholism,
        @Min(0) int dailyBrushing,
        @Min(0) boolean flossing,
        SystemicDiseases systemicDiseases,
        @Min(0) boolean continuousMedicationUse,
        PlanType planType
) {}