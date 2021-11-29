package com.ww.rabbitmq.eight;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

public class Producer {
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
//        AMQP.BasicProperties basicProperties =new AMQP.BasicProperties()
//                .builder().expiration("10000").build();
        for (int i = 1; i < 11; i++) {
            String message = "info:"+i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes());
        }
    }
}
