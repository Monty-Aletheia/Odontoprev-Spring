package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
}
