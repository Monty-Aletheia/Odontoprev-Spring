package com.fiap.br.microservice.application.service;

import com.fiap.br.microservice.application.repository.PatientExternalRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PatientRiskListener {

    private final PatientExternalRepository patientExternalRepository;

    public PatientRiskListener(PatientExternalRepository patientExternalRepository) {
        this.patientExternalRepository = patientExternalRepository;
    }

    @RabbitListener(queues = "patient-risk-queue")
    public void receberMensagem(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);

    }
}
