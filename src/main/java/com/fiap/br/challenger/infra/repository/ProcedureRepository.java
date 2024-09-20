package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcedureRepository extends JpaRepository<Procedure, UUID>{
}
