package com.fiap.br.challenger.application.service.mapper;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.patient.Patient;
import com.fiap.br.challenger.infra.repository.DentistRepository;
import com.fiap.br.challenger.infra.repository.PatientRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ConsultationMapper {

    @Autowired
    protected PatientRepository patientRepository;

    @Autowired
    protected DentistRepository dentistRepository;

    @Autowired
    protected PatientMapper patientMapper;

    @Autowired
    protected DentistMapper dentistMapper;

    public Consultation toModel(ConsultationRequestDTO dto) {
        Consultation consultation = new Consultation();
        consultation.setDate(dto.date());
        consultation.setValue(BigDecimal.valueOf(dto.value()));
        consultation.setPatient(getPatientById(dto.patient()));
        consultation.setDentist(getDentistById(dto.dentist()));
        return consultation;
    }

    public ConsultationResponseDTO toDTO(Consultation consultation) {
        return new ConsultationResponseDTO(
                consultation.getId(),
                consultation.getDate(),
                consultation.getValue().doubleValue(),
                consultation.getRiskStatus(),
                consultation.getDescription(),
                patientMapper.toDto(consultation.getPatient()),
                dentistMapper.toDto(consultation.getDentist())
        );
    }

    protected Patient getPatientById(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
    }

    protected Dentist getDentistById(UUID id) {
        return dentistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dentist not found with id: " + id));
    }
}
