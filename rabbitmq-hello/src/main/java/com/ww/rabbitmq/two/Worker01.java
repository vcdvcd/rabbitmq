package com.ww.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker01 {
    public static final String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        System.out.println("Cq ok");
        channel.basicConsume(QUEUE_NAME, true,
                (consumerTag, message) -> System.out.println(new String(message.getBody())), System.out::println);
    }
}
