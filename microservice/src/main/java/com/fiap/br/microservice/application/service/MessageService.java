package com.fiap.br.microservice.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fiap.br.microservice.application.entity.RiskStatus;
import com.fiap.br.microservice.application.repository.PatientExternalRepository;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class MessageService {

    private final PatientExternalRepository patientExternalRepository;
    private final RestTemplate restTemplate;
    private final String apiUrl = "https://aletheia-ai.azurewebsites.net/predict";

    public MessageService(PatientExternalRepository patientExternalRepository) {
        this.patientExternalRepository = patientExternalRepository;
        this.restTemplate = new RestTemplate();
    }

    public void predictPatientRisk(JsonNode rootJson) {
        JsonNode riskNodeJson = rootJson.get("risk");

        JSONObject patientRiskAssessmentJSON = new JSONObject(riskNodeJson.toString());

        JSONObject response = sendPatientDataToAletheIA(patientRiskAssessmentJSON);

        double riskProbability = response.getDouble("risk_probability");

        System.out.println("Probabilidade de risco: " + riskProbability);

        RiskStatus risk = RiskStatus.calcRiskStatus(riskProbability);
        UUID patientId = UUID.fromString(rootJson.get("patient_id").asText());
        patientExternalRepository.updateRiskStatus(patientId, risk.toString());
    }

    private JSONObject sendPatientDataToAletheIA(JSONObject patientRiskAssessmentJSON) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(patientRiskAssessmentJSON.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                request,
                String.class
        );

        return new JSONObject(response.getBody());
    }
}
