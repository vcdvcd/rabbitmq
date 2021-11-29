package com.ww.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PriorityQueueConsumer {

    @RabbitListener(queues = "queueE")
    public void receivePriorityQueue(Message message){
        String msg = new String(message.getBody());
        log.info("接收到优先级队列的内容为：{}",msg);
    }
}
