package com.fiap.br.challenger.application.service.mq;

import com.fiap.br.challenger.application.dto.patient.PatientRiskAssessmentDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PatientRiskMessageBuilder {

    public JSONObject build(UUID patientId, PatientRiskAssessmentDTO dto) {
        JSONObject json = new JSONObject();
        json.put("patient_id", patientId);

        JSONObject risk = new JSONObject();
        risk.put("idade", dto.age());
        risk.put("genero", dto.gender());
        risk.put("frequencia_consultas", dto.consultationFrequency());
        risk.put("aderencia_tratamento", 0);
        risk.put("historico_caries", dto.cavitiesHistory());
        risk.put("doenca_periodontal", dto.periodontalDisease());
        risk.put("numero_implantes", dto.numberOfImplants());
        risk.put("fumante", dto.smoker());
        risk.put("alcoolismo", dto.alcoholism());
        risk.put("escovacao_diaria", dto.dailyBrushing());
        risk.put("uso_fio_dental", dto.flossing());
        risk.put("doencas_sistemicas", dto.systemicDiseases().getDescricao());
        risk.put("medicamentos_uso_continuo", dto.continuousMedicationUse());
        risk.put("numero_sinistros_previos", 0);
        risk.put("valor_medio_sinistros", 0);
        risk.put("tratamentos_complexos_previos", dto.previousComplexTreatments());
        risk.put("tipo_plano", dto.planType().getDescricao());

        json.put("risk", risk);
        return json;
    }
}

