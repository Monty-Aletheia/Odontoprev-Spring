package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_dentist")
@Data
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String specialty;

    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "claims_rate", columnDefinition = "DECIMAL(5, 2) DEFAULT 0.00")
    private Double claimsRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status", nullable = false)
    private RiskStatus riskStatus;

    @ManyToMany(mappedBy = "dentists", cascade = CascadeType.ALL)
    private List<Consultation> consultations;
}
