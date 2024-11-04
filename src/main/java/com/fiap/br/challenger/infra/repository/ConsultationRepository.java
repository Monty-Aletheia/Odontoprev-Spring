package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.Date;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {

    Optional<Consultation> findById(UUID id);

    @Procedure(procedureName = "insert_consultation")
    void insertConsultation(Date p_consultation_date, Double p_consultation_value, String p_risk_status, String p_description, UUID p_patient_id);

    @Procedure(procedureName = "update_consultation")
    void updateConsultation(UUID p_id, Date p_consultation_date, Double p_consultation_value, String p_risk_status, String p_description);

    @Procedure(procedureName = "delete_consultation")
    void deleteConsultation(UUID p_id);
}
