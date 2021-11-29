package com.ww.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriorityQueueConfig {

    @Bean
    public DirectExchange cExchange(){
        return new DirectExchange("C");
    }

    @Bean
    public Queue queueE(){
        return QueueBuilder.nonDurable("queueE").maxPriority(10).build();
    }
    @Bean
    public Binding xBindingQueueE() {
        return BindingBuilder.bind(queueE()).to(cExchange()).with("CE");
    }

}
