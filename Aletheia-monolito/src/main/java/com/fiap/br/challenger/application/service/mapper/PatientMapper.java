package com.fiap.br.challenger.application.service.mapper;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.domain.model.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientResponseDTO toDto(Patient patient);

    @Mapping(target = "id", ignore = true)
    Patient toEntity(PatientRequestDTO dto);

    default void updateEntityFromDto(Patient entity, PatientRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setBirthday(dto.getBirthday());
        entity.setGender(dto.getGender());
    }
}
