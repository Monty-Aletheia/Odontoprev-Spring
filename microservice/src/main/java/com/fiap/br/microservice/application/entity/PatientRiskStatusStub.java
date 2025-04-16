package com.fiap.br.microservice.application.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_patient")
public class PatientRiskStatusStub {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status")
    private RiskStatus riskStatus;
}