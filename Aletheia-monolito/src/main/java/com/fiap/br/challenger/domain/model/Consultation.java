package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import com.fiap.br.challenger.domain.model.patient.Patient;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_consultation")
@Data
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "consultation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "consultation_value", nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status", nullable = false)
    private RiskStatus riskStatus;

    @Column()
    private String description;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private Dentist dentist;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private Set<Claim> claims;

    public Consultation() {}

    public Consultation(LocalDate date, BigDecimal value, Patient patient, Dentist dentist, RiskStatus riskStatus, String description) {
        this.date = date;
        this.value = value;
        this.patient = patient;
        this.dentist = dentist;
        this.riskStatus =  riskStatus;
        this.description = description;
    }
}
