package com.ww.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TtlQueueConfig {
    public static final String X_EXCHANGE = "X";
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    public static final String DEAD_LETTER_QUEUE = "QD";

    @Bean
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue queueA() {
        return QueueBuilder.nonDurable(QUEUE_A).ttl(10000).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("YD").build();
    }

    @Bean
    public Queue queueB() {
        return QueueBuilder.nonDurable(QUEUE_B).ttl(40000).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("YD").build();
    }



    @Bean
    public Queue queueC() {
        return QueueBuilder.nonDurable(QUEUE_C).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("YD").build();
    }

    @Bean
    public Queue queueD() {
        return QueueBuilder.nonDurable(DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Binding xBindingQueueA() {
        return BindingBuilder.bind(queueA()).to(xExchange()).with("XA");
    }


    @Bean
    public Binding xBindingQueueB() {
        return BindingBuilder.bind(queueB()).to(xExchange()).with("XB");
    }

    @Bean
    public Binding xBindingQueueC() {
        return BindingBuilder.bind(queueC()).to(xExchange()).with("XC");
    }

    @Bean
    public Binding yBindingQueueD() {
        return BindingBuilder.bind(queueD()).to(yExchange()).with("YD");
    }
}
