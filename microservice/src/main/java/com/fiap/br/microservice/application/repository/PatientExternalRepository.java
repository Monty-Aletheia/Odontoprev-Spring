package com.fiap.br.microservice.application.repository;

import com.fiap.br.microservice.application.entity.PatientRiskStatusStub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface PatientExternalRepository extends JpaRepository<PatientRiskStatusStub, UUID> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_patient SET risk_status = :riskStatus WHERE id = :id", nativeQuery = true)
    void updateRiskStatus(@Param("id") UUID id, @Param("riskStatus") String riskStatus);
}

