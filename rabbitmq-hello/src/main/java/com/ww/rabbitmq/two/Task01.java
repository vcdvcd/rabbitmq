package com.ww.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Task01 {
    public static final String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare("hello2",false,false,false,null);
        Scanner sc =new Scanner(System.in);
        while(sc.hasNext()){
            String next = sc.next();
            channel.basicPublish("",QUEUE_NAME,null,next.getBytes());
            System.out.println("ok");
        }
    }
}
