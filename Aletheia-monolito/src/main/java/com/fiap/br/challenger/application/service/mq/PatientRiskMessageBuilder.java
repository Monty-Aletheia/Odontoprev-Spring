package com.fiap.br.challenger.application.service.mq;

import com.fiap.br.challenger.application.dto.patient.PatientRiskAssessmentDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PatientRiskMessageBuilder {

    public JSONObject build(UUID pacienteId, PatientRiskAssessmentDTO dto) {
        JSONObject json = new JSONObject();
        json.put("pacient_id", pacienteId);

        JSONObject risco = new JSONObject();
        json.put("idade", dto.age());
        json.put("genero", dto.gender());
        json.put("frequencia_consultas", dto.consultationFrequency());
        json.put("aderencia_tratamento", 0);
        json.put("historico_caries", dto.cavitiesHistory());
        json.put("doenca_periodontal", dto.periodontalDisease());
        json.put("numero_implantes", dto.numberOfImplants());
        json.put("fumante", dto.smoker());
        json.put("alcoolismo", dto.alcoholism());
        json.put("escovacao_diaria", dto.dailyBrushing());
        json.put("uso_fio_dental", dto.flossing());
        json.put("doencas_sistemicas", dto.systemicDiseases().getDescricao());
        json.put("medicamentos_uso_continuo", dto.continuousMedicationUse());
        json.put("numero_sinistros_previos", 0);
        json.put("valor_medio_sinistros", 0);
        json.put("tratamentos_complexos_previos", dto.previousComplexTreatments());
        json.put("tipo_plano", dto.planType().getDescricao());

        json.put("risk", risco);
        return json;
    }
}

