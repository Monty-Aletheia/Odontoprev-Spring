package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findById(UUID id);

    @Procedure(procedureName = "insert_patient")
    void insertPatient(String p_name, Date p_birthday, String p_gender, String p_risk_status, Integer p_consultation_frequency, String p_associated_claims);

    @Procedure(procedureName = "update_patient")
    void updatePatient(UUID p_id, String p_name, Date p_birthday, String p_gender, String p_risk_status, Integer p_consultation_frequency, String p_associated_claims);

    @Procedure(procedureName = "delete_patient")
    void deletePatient(UUID p_id);
}
