package com.ww.rabbitmq.consumer;

import com.ww.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.WARING_QUEUE)
    public void receiveWarning(Message message) {
        String msg = new String(message.getBody());
        log.warn("报警队列中的消息为：{}", msg);
    }

}
