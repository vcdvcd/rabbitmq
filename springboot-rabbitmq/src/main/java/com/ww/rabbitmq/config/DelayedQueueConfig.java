package com.ww.rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedQueueConfig {
    public static final String DELAYED_EXCHANGE = "delayed.exchange";
    public static final String DELAYED_QUEUE = "delayed.queue";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    @Bean
    public CustomExchange delExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", false, false,
                arguments);
    }

    @Bean
    public Queue delQueue() {
        return QueueBuilder.nonDurable(DELAYED_QUEUE).build();
    }

    @Bean
    public Binding bindingToDelExchange() {
        return BindingBuilder.bind(delQueue()).to(delExchange()).with(DELAYED_ROUTING_KEY).noargs();
    }
}
