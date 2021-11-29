package com.ww.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {
    public static final String QUEUE_NAME="hello2";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("192.168.174.139");
        factory.setUsername("root");
        factory.setPassword("88023265");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(QUEUE_NAME,true,
                (consumerTag, message) -> System.out.println(new String(message.getBody())),System.out::println);
    }
}
