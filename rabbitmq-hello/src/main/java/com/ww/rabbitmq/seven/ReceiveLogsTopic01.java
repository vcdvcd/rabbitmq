package com.ww.rabbitmq.seven;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

public class ReceiveLogsTopic01 {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args)throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare("Q1",false,false,false,null);
        channel.queueBind("Q1",EXCHANGE_NAME,"*.orange.*");
        channel.basicConsume("Q1",true,((consumerTag, message) -> {
            System.out.println("Q1接受到"+"routingkey:"+message.getEnvelope().getRoutingKey()+"message:"+new String(message.getBody()));
        }),System.out::println);
    }
}
