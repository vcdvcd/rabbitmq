package com.ww.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class ReceiveLogs02 {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,EXCHANGE_NAME,"11");
        System.out.println("wait for message 2");
        channel.basicConsume(queue,true,(consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
            File file =new File("D://bqwgrabbitmq//bqwg.text");
            FileUtils.writeStringToFile(file,new String(message.getBody()),"utf-8",true);
            System.out.println("ez to save");
        },System.out::println);
    }
}
