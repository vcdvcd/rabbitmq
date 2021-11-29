package com.ww.rabbitmq.eight;

import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

public class Consumer02 {

    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        channel.basicConsume(DEAD_QUEUE,true,((consumerTag, message) -> System.out.println(new String(message.getBody()))
        ),System.out::println);
    }
}
