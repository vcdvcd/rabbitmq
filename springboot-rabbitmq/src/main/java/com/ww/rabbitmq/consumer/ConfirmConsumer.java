package com.ww.rabbitmq.consumer;

import com.ww.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    public void receiveMessage(Message message) {
        String msg = new String(message.getBody());
        log.info("消费者收到消息为：{}", msg);
    }
}
