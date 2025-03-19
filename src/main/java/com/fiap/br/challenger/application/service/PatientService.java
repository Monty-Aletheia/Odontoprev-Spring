package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRiskAssessmentDTO;
import com.fiap.br.challenger.application.service.mapper.PatientMapper;
import com.fiap.br.challenger.domain.model.patient.Patient;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import com.fiap.br.challenger.infra.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://aletheia-ai.azurewebsites.net/predict";

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

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
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO, PatientRiskAssessmentDTO patientRiskAssessmentDTO) {
        Patient patient = patientMapper.toEntity(patientRequestDTO);
        patient.setAssociatedClaims("");
        patient.setConsultationFrequency(0);
        patient.setRiskStatus(predictPatientRisk(patientRiskAssessmentDTO));
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDto(savedPatient);
    }

    @Transactional
    public Optional<PatientResponseDTO> updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not exist"));
        existingPatient.setAssociatedClaims("");
        existingPatient.setConsultationFrequency(0);
        existingPatient.setRiskStatus(RiskStatus.NENHUM);

        patientMapper.updateEntityFromDto(existingPatient, patientRequestDTO);

        Patient updatedPatient = patientRepository.save(existingPatient);

        return Optional.of(patientMapper.toDto(updatedPatient));
    }

    @Transactional
    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Patient of id: %s not found", id)));
        patientRepository.delete(patient);
    }

    public RiskStatus predictPatientRisk(PatientRiskAssessmentDTO dto) {
        JSONObject patientRiskAssessmentJSON = mapPatientRiskAssessmentDTOtoJSONObject(dto);

        JSONObject response = sendPatientDataToAletheIA(patientRiskAssessmentJSON);

        double riskProbability = response.getDouble("risk_probability");

        if (riskProbability <= 0.10) {
            return RiskStatus.BAIXO;
        }
        if (riskProbability >= 0.10 && riskProbability <= 0.40) {
            return RiskStatus.MEDIO;
        }
        return RiskStatus.ALTO;
    }

    public JSONObject mapPatientRiskAssessmentDTOtoJSONObject(PatientRiskAssessmentDTO dto) {
        JSONObject patientRiskAssessmentJSON = new JSONObject();
        patientRiskAssessmentJSON.put("idade", dto.age());
        patientRiskAssessmentJSON.put("genero", dto.gender());
        patientRiskAssessmentJSON.put("frequencia_consultas", dto.consultationFrequency());
        patientRiskAssessmentJSON.put("aderencia_tratamento", 0);
        patientRiskAssessmentJSON.put("historico_caries", dto.cavitiesHistory());
        patientRiskAssessmentJSON.put("doenca_periodontal", dto.periodontalDisease());
        patientRiskAssessmentJSON.put("numero_implantes", dto.numberOfImplants());
        patientRiskAssessmentJSON.put("fumante", dto.smoker());
        patientRiskAssessmentJSON.put("alcoolismo", dto.alcoholism());
        patientRiskAssessmentJSON.put("escovacao_diaria", dto.dailyBrushing());
        patientRiskAssessmentJSON.put("uso_fio_dental", dto.flossing());
        patientRiskAssessmentJSON.put("doencas_sistemicas", dto.systemicDiseases().getDescricao());
        patientRiskAssessmentJSON.put("medicamentos_uso_continuo", dto.continuousMedicationUse());
        patientRiskAssessmentJSON.put("numero_sinistros_previos", 0);
        patientRiskAssessmentJSON.put("valor_medio_sinistros", 0);
        patientRiskAssessmentJSON.put("tratamentos_complexos_previos", dto.previousComplexTreatments());
        patientRiskAssessmentJSON.put("tipo_plano", dto.planType().getDescricao());

        return patientRiskAssessmentJSON;
    }

    public JSONObject sendPatientDataToAletheIA(JSONObject patientRiskAssessmentJSON) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(patientRiskAssessmentJSON.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl, HttpMethod.POST, request, String.class
        );

        return new JSONObject(response.getBody());
    }
}
