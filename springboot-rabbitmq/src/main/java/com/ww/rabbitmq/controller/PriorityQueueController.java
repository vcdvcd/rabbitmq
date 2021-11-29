package com.ww.rabbitmq.controller;

import com.ww.rabbitmq.config.TtlQueueConfig;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriorityQueueController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDelayMsg/")
    public void sendMsg() {
        for (int i = 0; i < 10; i++) {
            int j = i;
            rabbitTemplate.convertAndSend("C", "CE", "info" + i,
                    msg -> {
                        if (j == 5)
                            msg.getMessageProperties().setDeliveryMode(MessageProperties.DEFAULT_DELIVERY_MODE);
                            msg.getMessageProperties().setPriority(5);
                        return msg;
                    });
        }
    }
}
