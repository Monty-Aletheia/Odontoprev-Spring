package com.fiap.br.challenger.domain.model;

import com.fiap.br.challenger.domain.model.enums.Categoria;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Procedimento")
@Data
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(name = "custo_médio", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private Double custoMédio;

    @Column(name = "frequência_esperada", columnDefinition = "INT DEFAULT 0")
    private Integer frequênciaEsperada;

    @Column(name = "indicador_risco")
    private Boolean indicadorRisco;
}