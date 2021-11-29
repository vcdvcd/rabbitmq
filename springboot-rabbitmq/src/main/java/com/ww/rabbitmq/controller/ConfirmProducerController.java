package com.ww.rabbitmq.controller;

import com.ww.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/confirm")
public class ConfirmProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 实验交换机接收不到的情况
     */
    @GetMapping("/sendMsg/{message}")
    public void sendMessage(@PathVariable String message) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE + "1", ConfirmConfig.CONFIRM_ROUTING_KEY, message,
                correlationData);
        log.info("发送消息为：{}", message);
    }

    /**
     * 实验队列接收不到的情况
     */
    @GetMapping("/sendMessage/{message}")
    public void sendMsg(@PathVariable String message) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, ConfirmConfig.CONFIRM_ROUTING_KEY, "key1" + message,
                correlationData);
        log.info("发送消息为：{}", message);
        CorrelationData correlationData2 = new CorrelationData();
        correlationData2.setId("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, "key12", "key12" + message,
                correlationData2);
        log.info("发送消息为：{}", message);
    }
}
