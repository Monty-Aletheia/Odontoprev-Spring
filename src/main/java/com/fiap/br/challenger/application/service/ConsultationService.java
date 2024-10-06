package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.application.service.mapper.ConsultationMapper;
import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.Patient;
import com.fiap.br.challenger.infra.repository.ConsultationRepository;
import com.fiap.br.challenger.infra.repository.DentistRepository;
import com.fiap.br.challenger.infra.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DentistRepository dentistRepository;

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
            throw new EntityNotFoundException("Paciente de id " + patientId);
        }

        List<Dentist> dentists = dentistRepository.findAllById(dentistIds);

        if(dentists.isEmpty()){
            throw new EntityNotFoundException("Dentista de id " + patientId);
        }


        consultation.setPatient(patient.get());
        consultation.setDentists(dentists);
        Consultation savedConsultation = consultationRepository.save(consultation);



        return consultationMapper.toDto(savedConsultation);
    }

    @Transactional
    public void deleteConsultation(UUID id) {
        if (!consultationRepository.existsById(id)) {
            throw new EntityNotFoundException("Consulta não encontrada: " + id);
        }
        consultationRepository.deleteById(id);
    }
}
