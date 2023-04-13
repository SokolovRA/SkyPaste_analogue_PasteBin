package com.example.test_work_4.listener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

public class MessageSender {
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.name}")
    private String queueName;

    public void sendMessage(PasteMessage pasteMessage) {
        rabbitTemplate.convertAndSend(queueName, pasteMessage);
    }
}

