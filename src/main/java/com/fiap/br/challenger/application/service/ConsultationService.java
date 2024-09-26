package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.application.service.mapper.ConsultationMapper;
import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.infra.repository.ConsultationRepository;
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
        Consultation savedConsultation = consultationRepository.save(consultation);
        return consultationMapper.toDto(savedConsultation);
    }

    public void deleteConsultation(UUID id) {
        if (!consultationRepository.existsById(id)) {
            throw new EntityNotFoundException("Consulta não encontrada: " + id);
        }
        consultationRepository.deleteById(id);
    }
}
