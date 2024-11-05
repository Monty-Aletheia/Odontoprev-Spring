package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.dentist.DentistRequestDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.service.mapper.DentistMapper;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.infra.repository.DentistRepository;
import jakarta.persistence.EntityExistsException;
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

    public Optional<DentistResponseDTO> getDentistsByUUID(UUID id) {
        return dentistRepository.findById(id).map(dentistMapper::toDto);
    }

    @Transactional
    public DentistResponseDTO createDentist(DentistRequestDTO dentistRequestDTO) {
        if (dentistRepository.findByRegistrationNumber(dentistRequestDTO.registrationNumber()).isPresent()) {
            throw new EntityExistsException("Dentista já existe");
        }

        Dentist dentist = dentistMapper.toEntity(dentistRequestDTO);
        Dentist savedDentist = dentistRepository.save(dentist);
        return dentistMapper.toDto(savedDentist);
    }

    @Transactional
    public Optional<DentistResponseDTO> updateDentist(UUID id, DentistRequestDTO dentistRequestDTO) {
        Dentist existingDentist = dentistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dentista não encontrado: " + id));

        dentistRepository.findByRegistrationNumber(dentistRequestDTO.registrationNumber())
                .filter(dentist -> !dentist.getId().equals(id))
                .ifPresent(dentist -> {
                    throw new EntityExistsException("Outro dentista com o mesmo número de registro já existe");
                });

        dentistMapper.updateEntityFromDto(existingDentist,dentistRequestDTO);

        Dentist updatedDentist = dentistRepository.save(existingDentist);

        return Optional.of(dentistMapper.toDto(updatedDentist));
    }

    @Transactional
    public void deleteDentist(UUID id) {
        if (!dentistRepository.existsById(id)) {
            throw new EntityNotFoundException("Dentista não existe: " + id);
        }
        dentistRepository.deleteById(id);    }
}
