package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, UUID> {
    Optional<Dentist> findById(UUID id);
}
