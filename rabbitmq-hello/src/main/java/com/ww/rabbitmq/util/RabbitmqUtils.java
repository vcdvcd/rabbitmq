package com.ww.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqUtils {
    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("192.168.174.139");
        factory.setUsername("root");
        factory.setPassword("88023265");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}
