package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.application.service.mapper.ConsultationMapper;
import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.User;
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

    public void createConsultation(ConsultationRequestDTO consultationRequestDTO) {
        Patient consultationPatient =  patientRepository.findById(consultationRequestDTO.patient()).get();
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

    public List<ConsultationResponseDTO> findByUserId(UUID userId){
        return consultationRepository.findByPatientUserId(userId).stream().map(consultationMapper::toDTO).collect(Collectors.toList());
    }

    public List<ConsultationResponseDTO> findAllConsultation(){
        return consultationRepository.findAll().stream().map(consultationMapper::toDTO).collect(Collectors.toList());
    }
}