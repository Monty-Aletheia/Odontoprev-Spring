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

    @Column(nullable = false)
    private String password;

    @Column()
    private String specialty;

    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "claims_rate")
    private Double claimsRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status")
    private RiskStatus riskStatus;

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL)
    private List<Consultation> consultations;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
