package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {

    Optional<Consultation> findById(UUID id);

}
