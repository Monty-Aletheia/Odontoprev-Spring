package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {

    Optional<Consultation> findById(UUID id);

    @Query("SELECT c FROM Consultation c WHERE c.patient.user.id = :userId")
    List<Consultation> findByPatientUserId(@Param("userId") UUID userId);
}
