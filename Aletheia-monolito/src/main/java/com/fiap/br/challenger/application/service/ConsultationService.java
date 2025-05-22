package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.service.mapper.ConsultationMapper;
import com.fiap.br.challenger.application.service.mapper.PatientMapper;
import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.User;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import com.fiap.br.challenger.domain.model.enums.Role;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private ConsultationMapper consultationMapper;
    @Autowired
    private PatientMapper patientMapper;

    public void createConsultation(ConsultationRequestDTO consultationRequestDTO, Patient patient) {
        Patient consultationPatient;
        consultationPatient = Objects.requireNonNullElseGet(patient, () -> patientRepository.findById(consultationRequestDTO.patient()).get());
        Dentist consultationDentist = dentistRepository.findById(consultationRequestDTO.dentist()).get();
        Consultation consultation =
                new Consultation(
                        consultationRequestDTO.date(),
                        BigDecimal.valueOf(consultationRequestDTO.value()),
                        consultationPatient,
                        consultationDentist,
                        consultationPatient.getRiskStatus(),
                        "teste");

        consultationRepository.save(consultation);
    }

    public List<ConsultationResponseDTO> findByUserId(UUID userId, Role userRole){
        if (userRole == Role.PATIENT){
            return consultationRepository.findByPatientUserId(userId).stream().map(consultationMapper::toDTO).collect(Collectors.toList());
        }
        return consultationRepository.findByDentistUserId(userId).stream().map(consultationMapper::toDTO).collect(Collectors.toList());

    }

    public List<ConsultationResponseDTO> findAllConsultation(){
        return consultationRepository.findAll().stream().map(consultationMapper::toDTO).collect(Collectors.toList());
    }

    public ConsultationResponseDTO findById(UUID id){
        return consultationMapper.toDTO(consultationRepository.findById(id).get());
    }


    public void updateConsultation(UUID id, ConsultationRequestDTO consultationRequestDTO) {
        Consultation existingConsultation = consultationRepository.findById(id).get();
        consultationMapper.updateEntityFromDto(existingConsultation, consultationRequestDTO);
        consultationRepository.save(existingConsultation);
    }

    public void deleteConsultation(UUID id) {
        consultationRepository.deleteById(id);
    }
}