package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.service.mapper.PatientMapper;
import com.fiap.br.challenger.domain.model.Patient;
import com.fiap.br.challenger.infra.repository.PatientRepository;
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
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public Optional<PatientResponseDTO> getPatientByUUID(UUID id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDto);
    }

    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = patientMapper.toEntity(patientRequestDTO);
        Patient savedPatient = patientRepository.insertPatient(patient);
        return patientMapper.toDto(savedPatient);
    }

    @Transactional
    public Optional<PatientResponseDTO> updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not exist"));

        patientMapper.updateEntityFromDto(existingPatient, patientRequestDTO);

        Patient updatedPatient = patientRepository.updatePatient(id, existingPatient);

        return Optional.of(patientMapper.toDto(updatedPatient));
    }

    @Transactional
    public void deletePatient(UUID id) {
        patientRepository.deletePatientWithErrorsHandled(id);
    }
}
