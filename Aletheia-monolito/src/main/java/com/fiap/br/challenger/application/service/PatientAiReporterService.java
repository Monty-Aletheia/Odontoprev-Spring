package com.fiap.br.challenger.application.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class PatientAiReporterService {

    private final ChatClient chatClient;

    public PatientAiReporterService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String createReport(String riskForm) {

        String system = """
                Você é responsável por criar um relatório de pacientes de um sistema "anti-sinistro" para clínicas odontológicas.
                Você receberá um formulário em formato JSON, enviado como mensagem do usuário. Ele contém informações sobre o paciente,
                como frequência de escovação, uso de fio dental, histórico médico e hábitos.
                
                Seu objetivo é gerar um relatório breve, em formato JSON, interpretando os dados recebidos e destacando os principais fatores de risco que
                devem ser levados em consideração para avaliar o risco de sinistro odontológico, ou seja, o seu papel é dizer para a empresa provedora de plano
                o quanto aquele paciente pode ser perigoso em relação ao acionamento do plano.
                
                Não repita os dados exatamente como estão — interprete-os.
                O relatório deve conter de 3 a 5 frases, com linguagem clara, objetiva e acessível.
                
                Me envie tanto em português como em inglês.
                
                Exemplo:
                
                Entrada:
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
                
                Saída esperada:
                
                {
                    "portuguese": "O paciente possui 20 anos de idade, e apresenta alguns fatores de risco, como escovação inadequada, ausência de uso de fio dental e histórico de tratamentos complexos.
                Mesmo sem doenças sistêmicas ou histórico de sinistros, os hábitos de higiene bucal e a falta de adesão a tratamentos exigem atenção.
                É recomendável reforçar a prevenção e manter acompanhamento contínuo.",
                
                    "english": "The patient is 20 years old and presents some risk factors, such as inadequate brushing, lack of flossing, and a history of complex treatments.
                                             Even without systemic diseases or a history of incidents, the oral hygiene habits and lack of adherence to treatments require attention.
                                             It is recommended to reinforce prevention and maintain continuous follow-up.",
                }
                
                Seja breve criativo no relatório.
                """;
        return this.chatClient.prompt().system(system   ).user(riskForm).call().content();
    }
}
