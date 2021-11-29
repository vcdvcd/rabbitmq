package com.ww.rabbitmq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class ReceiveLogsDirect02 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("disk",false,false,false,null);
        channel.queueBind("disk",EXCHANGE_NAME,"error");
        channel.basicConsume("disk",true,
                (consumerTag, message) ->{
                    System.out.println(new String(message.getBody()));
                    File file =new File("D://bqwgrabbitmq//error.txt");
                    FileUtils.writeStringToFile(file,new String(message.getBody()),"utf-8",true);
                    System.out.println("nice input");
                },System.out::println);
    }
}
