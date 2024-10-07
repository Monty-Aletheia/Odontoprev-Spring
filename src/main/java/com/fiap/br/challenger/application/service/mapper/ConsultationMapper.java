package com.fiap.br.challenger.application.service.mapper;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.domain.model.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {PatientMapper.class, DentistMapper.class})
public interface ConsultationMapper {

    ConsultationMapper INSTANCE = Mappers.getMapper(ConsultationMapper.class);

    ConsultationResponseDTO toDto(Consultation e);

    @Mapping(target = "id", ignore = true)
    Consultation toEntity(ConsultationRequestDTO dto);
}