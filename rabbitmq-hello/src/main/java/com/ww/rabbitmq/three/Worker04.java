package com.ww.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Worker04 {
    public static final String ACK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        System.out.println("c2 ok");
        int prefetchCount =5;
        channel.basicQos(prefetchCount);
        channel.basicConsume(ACK_QUEUE_NAME,false,((consumerTag, message) -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new String(message.getBody()));
            System.out.println("============");
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        }),System.out::println);
    }
}
