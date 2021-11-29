package com.ww.rabbitmq.seven;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

public class ReceiveLogsTopic02 {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args)throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare("Q2",false,false,false,null);
        channel.queueBind("Q2",EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind("Q2",EXCHANGE_NAME,"lazy.#");
        channel.basicConsume("Q2",true,((consumerTag, message) -> {
            System.out.println("Q2接受到"+"routingkey:"+message.getEnvelope().getRoutingKey()+"message:"+new String(message.getBody()));
        }),System.out::println);
    }
}
