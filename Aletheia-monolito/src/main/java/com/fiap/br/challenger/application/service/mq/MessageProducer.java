package com.fiap.br.challenger.application.service.mq;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(JSONObject message) {
        rabbitTemplate.convertAndSend("fiap.br.challenger", "patient-risk-exchange", message.toString());
    }

}
