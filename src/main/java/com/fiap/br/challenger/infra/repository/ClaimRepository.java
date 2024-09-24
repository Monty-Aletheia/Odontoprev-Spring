package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, UUID> {
    Optional<Claim> findByUuid(UUID uuid);
}
