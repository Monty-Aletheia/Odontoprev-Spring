package com.fiap.br.challenger.domain.model.enums;

import lombok.Getter;

@Getter
public enum PlanType {
    BASICO("basico"),
    INTERMEDIARIO("intermediario"),
    AVANCADO("avancado");

    private final String descricao;

    PlanType(String descricao) {
        this.descricao = descricao;
    }

}
