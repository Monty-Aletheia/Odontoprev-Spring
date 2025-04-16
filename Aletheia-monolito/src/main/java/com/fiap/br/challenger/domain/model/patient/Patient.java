package com.fiap.br.challenger.domain.model.patient;

import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.domain.model.enums.Gender;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_patient")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Consultation> consultations;
}
