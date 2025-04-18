package com.fiap.br.microservice.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PatientRiskListener {

    private final MessageService messageService;

    public PatientRiskListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @RabbitListener(queues = "patient-risk-queue")
    public void receiveMessage(String messageJson) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(messageJson);
            messageService.predictPatientRisk(root);
        } catch (Exception e) {
            System.err.println("Erro ao processar a mensagem JSON: " + e.getMessage());
        }
    }
}
