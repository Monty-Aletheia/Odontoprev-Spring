package com.fiap.br.challenger.application.service.mapper;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.domain.model.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    ConsultationResponseDTO toDto(Consultation e);

    @Mapping(target = "id", ignore = true)
    Consultation toEntity(ConsultationRequestDTO dto);

    default void updateEntityFromDto(Consultation entity, ConsultationRequestDTO dto) {
        entity.setConsultationDate(dto.consultationDate());
        entity.setConsultationValue(dto.consultationValue());
        entity.setDescription(dto.description());
        entity.setRiskStatus(dto.riskStatus());
    }
}