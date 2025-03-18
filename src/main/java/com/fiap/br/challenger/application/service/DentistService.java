package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.dentist.DentistRequestDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistUpdateDTO;
import com.fiap.br.challenger.application.service.mapper.DentistMapper;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.Patient;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import com.fiap.br.challenger.infra.repository.DentistRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DentistService {

    private final DentistRepository dentistRepository;
    private final DentistMapper dentistMapper;

    public List<DentistResponseDTO> getAllDentists() {
        return dentistRepository.findAll().stream().map(dentistMapper::toDto).toList();
    }

    public DentistResponseDTO getDentistsByUUID(UUID id) {
        return dentistRepository.findById(id).map(dentistMapper::toDto).orElseThrow(() -> new EntityNotFoundException("Dentist not found"));
    }

    @Transactional
    public DentistResponseDTO createDentist(DentistRequestDTO dentistRequestDTO) {
        Dentist dentist = dentistMapper.toEntity(dentistRequestDTO);
        dentist.setClaimsRate(0.0);
        dentist.setRiskStatus(RiskStatus.NENHUM);
        Dentist savedDentist = dentistRepository.save(dentist);
        return dentistMapper.toDto(savedDentist);
    }

    @Transactional
    public Optional<DentistResponseDTO> updateDentist(UUID id, DentistUpdateDTO dentistUpdateDTO) {
        Dentist existingDentist = dentistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dentist not found: " + id));

        dentistMapper.updateEntityFromDto(existingDentist, dentistUpdateDTO);

        Dentist updatedDentist = dentistRepository.save(existingDentist);

        return Optional.of(dentistMapper.toDto(updatedDentist));
    }

    @Transactional
    public void deleteDentist(UUID id) {
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Dentist of id: %s not found", id)));
        dentistRepository.delete(dentist);
    }
}
