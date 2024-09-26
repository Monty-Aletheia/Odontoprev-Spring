package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
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

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "consultation_value", nullable = false, columnDefinition = "DECIMAL(10, 2) NOT NULL")
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
