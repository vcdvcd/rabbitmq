package com.ww.rabbitmq.controller;

import com.ww.rabbitmq.config.DelayedQueueConfig;
import com.ww.rabbitmq.config.TtlQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/ttl")
@Slf4j
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendmsg(@PathVariable String message) {
        log.info("时间为：{},消息为：{}", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA", "发送一个延时10s的消息" + message);
        rabbitTemplate.convertAndSend("X", "XB", "发送一个延时40s的消息" + message);
    }

    @GetMapping("/sendMsg/{message}/{ttl}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttl) {
        log.info("发送时间为：{},延时时间为：{}，消息为：{}", new Date().toString(), ttl, message);
        rabbitTemplate.convertAndSend("X", "XC", "ttl" + message, msg -> {
            msg.getMessageProperties().setExpiration(ttl);
            return msg;
        });
    }

    @GetMapping("/sendDelayMsg/{message}/{delaytime}")
    public void sendDelayMsg(@PathVariable String message, @PathVariable Integer delaytime) {
        log.info("发送时间为：{}，延迟时间delaytime为：{}，消息为：{}", new Date().toString(), delaytime, message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE, DelayedQueueConfig.DELAYED_ROUTING_KEY
                , message, msg -> {
                    msg.getMessageProperties().setDelay(delaytime);
                    return msg;
                });
    }


}
