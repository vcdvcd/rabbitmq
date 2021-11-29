package com.ww.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//思路错误，应该要两台电脑来实现，可惜linux系统没有idea🤬
@Configuration
public class FederationConfig {
    public static final String FED_EXCHANGE = "fed_exchange";
    public static final String FED_EXCHANGE2 = "fed_exchange2";
    public static final String QUEUE1 = "node1_queue";
    public static final String QUEUE2 = "node2_queue";

    @Bean
    public DirectExchange fedExchange() {
        return new DirectExchange(FED_EXCHANGE, false, false);
    }

    @Bean
    public DirectExchange fedExchange2() {
        return new DirectExchange(FED_EXCHANGE2, false, false);
    }

    @Bean
    public Queue queue1() {
        return QueueBuilder.nonDurable(QUEUE1).build();
    }

    @Bean
    public Queue queue2(){
        return QueueBuilder.nonDurable(QUEUE2).build();
    }

    @Bean
    public Binding fedExchangeBindingQueue1() {
        return BindingBuilder.bind(queue1()).to(fedExchange()).with("k1");
    }

    @Bean
    public Binding fedExchange2BindingQueue2() {
        return BindingBuilder.bind(queue2()).to(fedExchange2()).with("k2");
    }
}
