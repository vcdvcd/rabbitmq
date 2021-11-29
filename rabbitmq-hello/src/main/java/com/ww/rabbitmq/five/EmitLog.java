package com.ww.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EmitLog {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        Scanner sc =new Scanner(System.in);
        while(sc.hasNext()){
            String next = sc.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,next.getBytes(StandardCharsets.UTF_8));
            System.out.println("ok");
        }
    }

}
