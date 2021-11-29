package com.ww.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Task2 {
    public static final String ACK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(ACK_QUEUE_NAME,true,false,false,null);
        Scanner sc =new Scanner(System.in);
        while(sc.hasNext()){
            String next = sc.next();
            channel.basicPublish("",ACK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,next.getBytes());
            System.out.println("ok");
        }
    }
}
