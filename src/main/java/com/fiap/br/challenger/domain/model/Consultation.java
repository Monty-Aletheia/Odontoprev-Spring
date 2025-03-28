package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import com.fiap.br.challenger.domain.model.patient.Patient;
import jakarta.persistence.*;
import lombok.Data;

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
    private LocalDate consultationDate;

    @Column(name = "consultation_value", nullable = false)
    private Double consultationValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_status", nullable = false)
    private RiskStatus riskStatus;

    @Column()
    private String description;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToMany
    @JoinTable(name = "consultation_dentist",
            joinColumns = @JoinColumn(name = "consultation_id"),
            inverseJoinColumns = @JoinColumn(name = "dentist_id"))
    private List<Dentist> dentists;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private Set<Claim> claims;
}
