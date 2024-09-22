package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_consultation")
@Data
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "consultation_value", nullable = false, columnDefinition = "DECIMAL(10, 2) NOT NULL")
    private Double consultationValue;

    @Column(name = "claims")
    private Boolean claims;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status", nullable = false)
    private RiskStatus riskStatus;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private Dentist dentist;
}
