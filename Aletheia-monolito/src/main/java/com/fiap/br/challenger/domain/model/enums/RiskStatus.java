package com.fiap.br.challenger.domain.model.enums;

import java.util.Locale;

public enum RiskStatus {
    NENHUM, BAIXO, MEDIO, ALTO;

    public String getDescription(String lang) {
        if (lang.equals("en")) {
            return switch (this) {
                case NENHUM -> "NONE";
                case BAIXO -> "LOW";
                case MEDIO -> "MEDIUM";
                case ALTO -> "HIGH";
                default -> this.name();
            };
        } else {
            // Para português (padrão) ou outros idiomas, retorna o nome original
            return this.name();
        }
    }
}