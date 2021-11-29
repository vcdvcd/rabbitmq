package com.ww.rabbitmq.controller;

import com.ww.rabbitmq.config.FederationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


//思路错误
@RestController
@Slf4j
public class FederationController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/bqwg/{message}")
    public void send(@PathVariable String message){
        rabbitTemplate.convertAndSend(FederationConfig.FED_EXCHANGE,"k1",message);
        log.info("消息已发出！");
    }
}
