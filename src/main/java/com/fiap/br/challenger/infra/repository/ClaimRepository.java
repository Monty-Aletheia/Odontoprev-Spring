package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.sql.Date;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, UUID> {
    Optional<Claim> findById(UUID id);

    @Procedure(procedureName = "insert_claim")
    void insertClaim(Date p_occurrence_date, Double p_value, String p_claim_type, String p_suggested_preventive_action, UUID p_consultation_id);

    @Procedure(procedureName = "update_claim")
    void updateClaim(UUID p_id, Date p_occurrence_date, Double p_value, String p_claim_type, String p_suggested_preventive_action, UUID p_consultation_id);

    @Procedure(procedureName = "delete_claim")
    void deleteClaim(UUID p_id);
}

