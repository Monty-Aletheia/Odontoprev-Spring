package com.fiap.br.challenger.domain.model.patient;
import com.fiap.br.challenger.domain.model.enums.PlanType;
import com.fiap.br.challenger.domain.model.enums.SystemicDiseases;
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
    boolean smoker;
    boolean alcoholism;
    int dailyBrushing;
    boolean flossing;
    SystemicDiseases systemicDiseases;
    boolean continuousMedicationUse;
    PlanType planType;
}