package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Dentist;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, UUID> {

    Optional<Dentist> findById(UUID id);

    Optional<Dentist> findByRegistrationNumber(String registrationNumber);

    default Dentist insertDentist(Dentist dentist) {
        try {
            UUID dentistId = insertDentist(
                    dentist.getName(),
                    dentist.getPassword(),
                    dentist.getSpecialty(),
                    dentist.getRegistrationNumber(),
                    dentist.getRiskStatus().toString()
            );

            return findById(dentistId)
                    .orElseThrow(() -> new RuntimeException("Dentist not found after insertion"));
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error inserting dentist: " + ex.getMessage(), ex);
        }
    }

    default Dentist updateDentist(UUID id, Dentist dentist) {
        try {
            UUID dentistId = updateDentist(
                    id,
                    dentist.getName(),
                    dentist.getPassword(),
                    dentist.getSpecialty(),
                    dentist.getRegistrationNumber(),
                    dentist.getRiskStatus().toString()
            );

            return findById(dentistId)
                    .orElseThrow(() -> new RuntimeException("Dentist not found after update"));
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error updating dentist: " + ex.getMessage(), ex);
        }
    }

    default void deleteDentistWithErrorsHandled(UUID dentistId) {
        try {
            deleteDentist(dentistId);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error deleting dentist: " + ex.getMessage(), ex);
        }
    }

    @Procedure(procedureName = "insert_dentist")
    UUID insertDentist(String p_name, String p_password, String p_specialty, String p_registration_number, String p_risk_status);

    @Procedure(procedureName = "update_dentist")
    UUID updateDentist(UUID p_id, String p_password, String p_name, String p_specialty, String p_registration_number, String p_risk_status);

    @Procedure(procedureName = "delete_dentist")
    void deleteDentist(UUID p_id);
}
