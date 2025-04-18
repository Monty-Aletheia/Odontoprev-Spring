package com.fiap.br.microservice.application.entity;

public enum RiskStatus {
    NENHUM, BAIXO, MEDIO, ALTO;

    public static RiskStatus calcRiskStatus(double riskProbability) {
        if (riskProbability <= 0.20) {
            return RiskStatus.BAIXO;
        }
        if (riskProbability >= 0.20 && riskProbability <= 0.40) {
            return RiskStatus.MEDIO;
        }
        return RiskStatus.ALTO;
    }
}
