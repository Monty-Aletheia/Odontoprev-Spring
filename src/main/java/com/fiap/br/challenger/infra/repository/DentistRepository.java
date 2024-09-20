package com.fiap.br.challenger.infra.repository;

import com.fiap.br.challenger.domain.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DentistRepository extends JpaRepository<Dentist, UUID> {

}
