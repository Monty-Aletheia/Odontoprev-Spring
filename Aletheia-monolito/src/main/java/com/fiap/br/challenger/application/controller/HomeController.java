package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.service.PatientAiReporterService;
import com.fiap.br.challenger.domain.model.enums.RiskStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PatientAiReporterService patientAiReporterService;

    public HomeController(PatientAiReporterService patientAiReporterService) {
        this.patientAiReporterService = patientAiReporterService;
    }

    @GetMapping
    public String redirectToHome(){
        return "redirect:home";
    }

    @GetMapping("/home")
    public String showHome(){
        return "home";
    }

    @GetMapping("/chat")
    public String chatTest(){
        return patientAiReporterService.createReport("""
                {
                  "idade": 20,
                  "genero": "M",
                  "frequencia_consultas": 0,
                  "aderencia_tratamento": 0,
                  "historico_caries": 1,
                  "doenca_periodontal": 0,
                  "numero_implantes": 1,
                  "tratamentos_complexos_previos": 3,
                  "fumante": 0,
                  "alcoolismo": 0,
                  "escovacao_diaria": 0,
                  "uso_fio_dental": 0,
                  "doencas_sistemicas": "Nenhuma",
                  "medicamentos_uso_continuo": 1,
                  "numero_sinistros_previos": 0,
                  "valor_medio_sinistros": 500.00,
                  "tipo_plano": "basico"
                }
                """);
    }
}
