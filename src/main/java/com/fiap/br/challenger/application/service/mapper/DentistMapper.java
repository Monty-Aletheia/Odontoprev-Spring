package com.fiap.br.challenger.application.service.mapper;

import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistRequestDTO;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DentistMapper {

    DentistResponseDTO toDto(Dentist dentist);

    @Mapping(target = "id", ignore = true)
    Dentist toEntity(DentistRequestDTO dto);

    default void updateEntityFromDto(Dentist entity, DentistRequestDTO dto) {
        entity.setName(dto.name());
        entity.setSpecialty(dto.specialty());
        entity.setClaimsRate(0.0);
        entity.setRegistrationNumber(dto.registrationNumber());
        entity.setRiskStatus(RiskStatus.NENHUM);
    }
}
