package com.fiap.br.challenger.domain.model.enums;

public enum PlanType {
    BASICO("basico"),
    INTERMEDIARIO("intermediario"),
    AVANCADO("avancado");

    private final String descricao;

    PlanType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
