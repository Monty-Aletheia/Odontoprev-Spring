package com.fiap.br.challenger.application.dto.patient;
import jakarta.validation.constraints.*;

public record PatientRiskAssessmentDTO(
        @Min(0) int age,
        @NotNull String gender,
        @Min(0) int consultationFrequency,
        @Min(0) int treatmentAdherence,
        @Min(0) int cavitiesHistory,
        @Min(0) int periodontalDisease,
        @Min(0) int numberOfImplants,
        @Min(0) int previousComplexTreatments,
        @Min(0) int smoker,
        @Min(0) int alcoholism,
        @Min(0) int dailyBrushing,
        @Min(0) int flossing,
        @NotNull String systemicDiseases,
        @Min(0) int continuousMedicationUse,
        @Min(0) int previousClaimsCount,
        @DecimalMin("0.0") double averageClaimValue,
        @NotNull String planType
) {}