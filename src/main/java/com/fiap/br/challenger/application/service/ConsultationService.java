package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.application.service.mapper.ConsultationMapper;
import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.patient.Patient;
import com.fiap.br.challenger.infra.repository.ConsultationRepository;
import com.fiap.br.challenger.infra.repository.DentistRepository;
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
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;

    public List<ConsultationResponseDTO> getAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto)
                .toList();
    }

    public Optional<ConsultationResponseDTO> getConsultationByUUID(UUID id) {
        return consultationRepository.findById(id)
                .map(consultationMapper::toDto);
    }

    @Transactional
    public ConsultationResponseDTO createConsultation(ConsultationRequestDTO consultationRequestDTO) {
        Consultation consultation = consultationMapper.toEntity(consultationRequestDTO);
        UUID patientId = consultationRequestDTO.patientId();
        List<UUID> dentistIds = consultationRequestDTO.dentistIds();
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isEmpty()) {
            throw new EntityNotFoundException("Patient id:" + patientId + " does not exist");
        }

        List<Dentist> dentists = dentistRepository.findAllById(dentistIds);

        if(dentists.isEmpty()){
            throw new EntityNotFoundException("Dentists with ids: " + dentistIds + " does not exist");
        }

        consultation.setPatient(patient.get());
        consultation.setDentists(dentists);
        Consultation savedConsultation = consultationRepository.save(consultation);

        return consultationMapper.toDto(savedConsultation);
    }

    @Transactional
    public Optional<ConsultationResponseDTO> updateConsultation(UUID id, ConsultationRequestDTO consultationRequestDTO) {
        Consultation existingConsultation = consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + id));

        UUID patientId = consultationRequestDTO.patientId();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient id:" + patientId + " does not exist"));

        List<UUID> dentistIds = consultationRequestDTO.dentistIds();
        List<Dentist> dentists = dentistRepository.findAllById(dentistIds);
        if (dentists.size() != dentistIds.size()) {
            throw new EntityNotFoundException("One or more dentists were not found with the provided IDs");
        }

        consultationMapper.updateEntityFromDto(existingConsultation,consultationRequestDTO);
        existingConsultation.setPatient(patient);
        existingConsultation.setDentists(dentists);

        Consultation updatedConsultation = consultationRepository.save(existingConsultation);

        return Optional.ofNullable(consultationMapper.toDto(updatedConsultation));
    }

    @Transactional
    public void deleteConsultation(UUID id) {
        if (!consultationRepository.existsById(id)) {
            throw new EntityNotFoundException("Consultation not found: " + id);
        }
        consultationRepository.deleteById(id);
    }
}