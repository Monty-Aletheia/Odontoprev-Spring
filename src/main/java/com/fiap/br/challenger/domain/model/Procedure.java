package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Procedure")
@Data
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(name = "average_cost", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private Double averageCost;

    @Column(name = "expected_frequency", columnDefinition = "INT DEFAULT 0")
    private Integer expectedFrequency;

    @Column(name = "risk_indicator")
    private Boolean riskIndicator;
}
