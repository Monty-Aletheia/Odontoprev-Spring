package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.service.mapper.PatientMapper;
import com.fiap.br.challenger.domain.model.Patient;
import com.fiap.br.challenger.infra.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public Optional<PatientResponseDTO> getPatientByUUID(UUID id) {
        return patientRepository.findByUuid(id)
                .map(patientMapper::toDto);
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = patientMapper.toEntity(patientRequestDTO);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDto(savedPatient);
    }

    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }
}
