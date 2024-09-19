package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.Gender;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_patient")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status", nullable = false)
    private RiskStatus riskStatus;

    @Column(name = "consultation_frequency", columnDefinition = "INT DEFAULT 0")
    private Integer consultationFrequency;

    @Lob
    @Column(name = "associated_claims")
    private String associatedClaims;

}
