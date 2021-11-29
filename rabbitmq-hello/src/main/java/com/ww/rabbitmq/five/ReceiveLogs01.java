package com.ww.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

public class ReceiveLogs01 {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args)throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,EXCHANGE_NAME,"1");
        System.out.println("wait for message");
        channel.basicConsume(queue,true,((consumerTag, message) -> System.out.println(new String(message.getBody())))
                ,System.out::println);
    }
}
