package com.fiap.br.challenger.application.service.mapper;

import com.fiap.br.challenger.application.dto.claim.ClaimRequestDTO;
import com.fiap.br.challenger.application.dto.claim.ClaimResponseDTO;
import com.fiap.br.challenger.domain.model.Claim;
import com.fiap.br.challenger.domain.model.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    @Mapping(target = "consultationId", source = "consultation.id")
    ClaimResponseDTO toDto(Claim claim);

    @Mapping(target = "id", ignore = true)
    Claim toEntity(ClaimRequestDTO dto);
}
