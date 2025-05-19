package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findById(UUID id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_patient SET ai_report = :aiReport WHERE id = :id", nativeQuery = true)
    void updatePatientAiReport(@Param("id") UUID id, @Param("aiReport") String aiReport);
}
