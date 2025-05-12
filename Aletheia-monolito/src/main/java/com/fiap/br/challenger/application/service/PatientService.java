package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.auth.UserRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRiskAssessmentDTO;
import com.fiap.br.challenger.application.service.mapper.PatientMapper;
import com.fiap.br.challenger.application.service.mq.MessageProducer;
import com.fiap.br.challenger.application.service.mq.PatientRiskMessageBuilder;
import com.fiap.br.challenger.application.service.utils.NameFormatter;
import com.fiap.br.challenger.domain.model.User;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import com.fiap.br.challenger.domain.model.enums.Role;
import com.fiap.br.challenger.domain.model.patient.Patient;
import com.fiap.br.challenger.infra.repository.PatientRepository;
import com.fiap.br.challenger.infra.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final PatientRiskMessageBuilder messageBuilder;

    private final MessageProducer producer;

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final UserService userService;
//    private final String apiUrl = "https://aletheia-ai.azurewebsites.net/predict";

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public PatientResponseDTO getPatientByUUID(UUID id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            throw new EntityNotFoundException("Patient not found with id " + id);
        }
        return patientMapper.toDto(optionalPatient.get());
    }

    @Transactional
    public void createPatient(PatientRequestDTO patientRequestDTO, PatientRiskAssessmentDTO patientRiskAssessmentDTO) {
        Patient patient = patientMapper.toEntity(patientRequestDTO);
        String name = NameFormatter.formatName(patientRequestDTO.getName());
        patient.setName(name);
        patient.setAssociatedClaims("");
        patient.setConsultationFrequency(0);
        patient.setRiskStatus(RiskStatus.NENHUM); // default

        User user = new User(Role.PATIENT, patientRequestDTO.getPassword(), patientRequestDTO.getEmail(), name);
        userService.createUser(user);
        patient.setUser(user);

        Patient savedPatient = patientRepository.save(patient);

        this.processRisk(savedPatient.getId(), patientRiskAssessmentDTO);

        patientMapper.toDto(savedPatient);
    }

    @Transactional
    public void updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not exist"));
        existingPatient.setAssociatedClaims("");
        existingPatient.setConsultationFrequency(0);
        existingPatient.setRiskStatus(RiskStatus.NENHUM);

        patientMapper.updateEntityFromDto(existingPatient, patientRequestDTO);

        Patient updatedPatient = patientRepository.save(existingPatient);

        patientMapper.toDto(updatedPatient);
    }

    @Transactional
    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Patient of id: %s not found", id)));
        patientRepository.delete(patient);
    }

    public void processRisk(UUID pacienteId, PatientRiskAssessmentDTO dto) {
        JSONObject message = messageBuilder.build(pacienteId, dto);
        producer.sendMessage(message);
    }
}
