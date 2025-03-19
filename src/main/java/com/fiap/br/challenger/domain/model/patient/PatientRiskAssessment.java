package com.fiap.br.challenger.domain.model.patient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientRiskAssessment{
    int age;
    String gender;
    int consultationFrequency;
    int cavitiesHistory;
    int periodontalDisease;
    int numberOfImplants;
    int previousComplexTreatments;
    int smoker;
    int alcoholism;
    int dailyBrushing;
    int flossing;
    String systemicDiseases;
    int continuousMedicationUse;
    String planType;
}