package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, UUID> {

    Optional<Dentist> findById(UUID id);

    Optional<Dentist> findByRegistrationNumber(String registrationNumber);

    @Procedure(procedureName = "insert_dentist")
    void insertDentist(String p_name, String p_specialty, String p_registration_number, String p_risk_status);

    @Procedure(procedureName = "update_dentist")
    void updateDentist(UUID p_id, String p_name, String p_specialty, String p_registration_number, String p_risk_status);

    @Procedure(procedureName = "delete_dentist")
    void deleteDentist(UUID p_id);
}
