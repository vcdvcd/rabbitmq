package com.ww.rabbitmq.consumer;

import com.ww.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DelayedQueueConsumer {
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE)
    public void receive(Message message){
        String msg =new String(message.getBody());
        log.info("接受时间为：{}，接受到的信息为：{}",new Date().toString(),msg);
    }
}
