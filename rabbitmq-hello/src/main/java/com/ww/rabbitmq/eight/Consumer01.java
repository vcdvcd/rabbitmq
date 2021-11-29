package com.ww.rabbitmq.eight;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.util.HashMap;
import java.util.Map;

public class Consumer01 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "lisi");
        arguments.put("x-max-length",6);
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        channel.basicConsume(NORMAL_QUEUE,false,((consumerTag, message) ->{
            if ("info:5".equals(new String(message.getBody()))){
                System.out.println("C1拒绝了你🤬！！！"+new String(message.getBody()));
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else{
                System.out.println("这是C1对你的恩惠😊！！！"+new String(message.getBody()));
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
        }),System.out::println);
    }
}
