package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistRequestDTO;
import com.fiap.br.challenger.application.service.mapper.DentistMapper;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.infra.repository.DentistRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DentistService {

    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private DentistMapper dentistMapper;

    public List<DentistResponseDTO> getAllDentists() {
        return dentistRepository.findAll().stream().map(dentistMapper::toDto).toList();
    }

    public Optional<DentistResponseDTO> getDentistsByUUID(UUID id) {
        return dentistRepository.findByUuid(id).map(dentistMapper::toDto);
    }

    @Transactional
    public DentistResponseDTO createDentist(DentistRequestDTO dentistRequestDTO) {
        Dentist dentist = dentistMapper.toEntity(dentistRequestDTO);
        Dentist savedDentist = dentistRepository.save(dentist);
        return dentistMapper.toDto(savedDentist);
    }

    public void deleteDentist(UUID id) {
        if (!dentistRepository.existsById(id)) {
            throw new EntityNotFoundException("Dentista não existe: " + id);
        }
        dentistRepository.deleteById(id);    }
}
