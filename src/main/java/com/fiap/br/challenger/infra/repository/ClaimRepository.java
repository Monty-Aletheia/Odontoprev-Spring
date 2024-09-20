package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClaimRepository extends JpaRepository<Claim, UUID> {
}
