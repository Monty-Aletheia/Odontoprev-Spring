package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Patient;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findById(UUID id);

    default Patient insertPatient(Patient patient) {
        try {
            UUID patientId = insertPatient(
                    patient.getName(),
                    patient.getBirthday(),
                    patient.getGender().toString(),
                    patient.getRiskStatus().toString(),
                    patient.getConsultationFrequency(),
                    patient.getAssociatedClaims()
            );

            return findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found after insertion"));
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error inserting patient: " + ex.getMessage(), ex);
        }
    }

    default Patient updatePatient(UUID id, Patient patient) {
        try {
            UUID patientId = updatePatient(
                    id,
                    patient.getName(),
                    patient.getBirthday(),
                    patient.getGender().toString(),
                    patient.getRiskStatus().toString(),
                    patient.getConsultationFrequency(),
                    patient.getAssociatedClaims()
            );

            return findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found after update"));
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error updating patient: " + ex.getMessage(), ex);
        }
    }

    default void deletePatientWithErrorsHandled(UUID patientId) {
        try {
            deletePatient(patientId);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error deleting patient: " + ex.getMessage(), ex);
        }
    }

    @Procedure(procedureName = "insert_patient")
    UUID insertPatient(String p_name, LocalDate p_birthday, String p_gender, String p_risk_status, Integer p_consultation_frequency, String p_associated_claims);

    @Procedure(procedureName = "update_patient")
    UUID updatePatient(UUID p_id, String p_name, LocalDate p_birthday, String p_gender, String p_risk_status, Integer p_consultation_frequency, String p_associated_claims);

    @Procedure(procedureName = "delete_patient")
    void deletePatient(UUID p_id);
}
