package com.fiap.br.challenger;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange patientRiskExchange() {
        return new DirectExchange("fiap.br.challenger");
    }

    @Bean
    public Queue patientRiskQueue() {
        return new Queue("patient-risk-queue", true);
    }

    @Bean
    public Binding binding(Queue patientRiskQueue, DirectExchange patientRiskExchange) {
        return BindingBuilder
                .bind(patientRiskQueue)
                .to(patientRiskExchange)
                .with("patient-risk-exchange");
    }
}

