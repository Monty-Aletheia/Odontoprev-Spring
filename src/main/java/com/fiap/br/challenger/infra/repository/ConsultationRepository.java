package com.fiap.br.challenger.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsultationRepository extends JpaRepository<ConsultationRepository, UUID> {
}
