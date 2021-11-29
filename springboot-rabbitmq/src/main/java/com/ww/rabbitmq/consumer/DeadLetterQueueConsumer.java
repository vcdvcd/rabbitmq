package com.ww.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void consumeMsg(Message message){
        log.info("收到时间为：{}，收到消息为：{}",new Date().toString(),new String(message.getBody()));
    }

}
