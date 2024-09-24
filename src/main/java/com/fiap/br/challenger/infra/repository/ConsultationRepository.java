package com.fiap.br.challenger.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<ConsultationRepository, UUID> {
}
