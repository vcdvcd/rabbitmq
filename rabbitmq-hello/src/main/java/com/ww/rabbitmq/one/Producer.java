package com.ww.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static final String QUEUE_NAME="hello2";

    public static void main(String[] args) {
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("192.168.174.141");
        factory.setUsername("admin");
        factory.setPassword("123");
        String msg ="hello bqwg wgnb!";
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
