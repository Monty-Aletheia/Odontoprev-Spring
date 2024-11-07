package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Consultation;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {

    Optional<Consultation> findById(UUID id);

    default Consultation insertConsultation(Consultation consultation) {
        try {
            List<String> dentistIds = consultation.getDentists().stream()
                    .map(dentist -> dentist.getId().toString())
                    .toList();

            UUID consultationId = insertConsultation(
                    consultation.getConsultationDate(),
                    consultation.getConsultationValue(),
                    consultation.getRiskStatus().toString(),
                    consultation.getDescription(),
                    consultation.getPatient().getId(),
                    dentistIds
            );

            return findById(consultationId)
                    .orElseThrow(() -> new RuntimeException("Consultation not found after insertion"));

        } catch (DataAccessException ex) {
            throw new RuntimeException("Error inserting consultation: " + ex.getMessage(), ex);
        }
    }

    default Consultation updateConsultation(UUID id, Consultation consultation) {

        try {
            List<String> dentistIds = consultation.getDentists().stream()
                    .map(dentist -> dentist.getId().toString())
                    .toList();

            UUID consultationId = updateConsultation(
                    id,
                    consultation.getConsultationDate(),
                    consultation.getConsultationValue(),
                    consultation.getRiskStatus().toString(),
                    consultation.getDescription(),
                    dentistIds
            );

            return findById(consultationId)
                    .orElseThrow(() -> new RuntimeException("Consultation not found after update"));

        } catch (DataAccessException ex) {
            throw new RuntimeException("Error updating consultation: " + ex.getMessage(), ex);
        }
    }

    default void deleteConsultationWithErrorsHandled(UUID consultationId) {
        try {
            deleteConsultation(consultationId);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error deleting consultation: " + ex.getMessage(), ex);
        }
    }

    @Procedure(procedureName = "insert_consultation")
    UUID insertConsultation(LocalDate p_consultation_date, Double p_consultation_value, String p_risk_status, String p_description, UUID p_patient_id, List<String> p_dentist_ids);

    @Procedure(procedureName = "update_consultation")
    UUID updateConsultation(UUID p_id, LocalDate p_consultation_date, Double p_consultation_value, String p_risk_status, String p_description, List<String> dentistIds);

    @Procedure(procedureName = "delete_consultation")
    void deleteConsultation(UUID p_id);
}
