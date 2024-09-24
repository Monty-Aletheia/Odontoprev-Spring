package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByUuid(UUID uuid);
}
