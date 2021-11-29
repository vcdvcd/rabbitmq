package com.ww.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class ConfirmConfig {
    public static final String CONFIRM_EXCHANGE = "confirm.exchange";
    public static final String CONFIRM_QUEUE = "confirm.queue";
    public static final String CONFIRM_ROUTING_KEY = "key1";
    public static final String BACKUP_EXCHANGE = "backup.exchange";
    public static final String BACKUP_QUEUE = "backup.queue";
    public static final String WARING_QUEUE = "warning.queue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(confirmCallback());
        rabbitTemplate.setReturnsCallback(returnsCallback());
    }

    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean
    public Queue backupQueue() {
        return new Queue(BACKUP_QUEUE, false, false, false);
    }

    @Bean
    public Queue warningQueue() {
        return new Queue(WARING_QUEUE, false, false, false);
    }

    @Bean
    public Binding backupExchangeBindingBackupQueue() {
        return BindingBuilder.bind(backupQueue()).to(backupExchange());
    }

    @Bean
    public Binding backupExchangeBindingWarningQueue() {
        return BindingBuilder.bind(warningQueue()).to(backupExchange());
    }

    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return (correlationData, ack, cause) -> {
            String id = correlationData != null ? correlationData.getId() : "";
            if (ack)
                log.info("交换机成功接受到id为：{}的消息", id);
            else
                log.info("交换机还未接受到id为：{}的消息，原因为：{}", id, cause);

        };
    }

    @Bean
    public RabbitTemplate.ReturnsCallback returnsCallback() {
        return returned -> {
            log.error("队列回退的消息为：{}，回退码为：{}，回退原因：{}，交换机为：{}，路由key为：{}",
                    new String(returned.getMessage().getBody()), returned.getReplyCode()
                    , returned.getReplyText(), returned.getExchange(), returned.getRoutingKey());
        };
    }

    @Bean
    public DirectExchange confirmExchange() {
        return new ExchangeBuilder(CONFIRM_EXCHANGE, "direct").alternate(BACKUP_EXCHANGE).build();
    }

    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.nonDurable(CONFIRM_QUEUE).build();
    }

    @Bean
    public Binding exchangeBindingQueue() {
        return BindingBuilder.bind(confirmQueue()).to(confirmExchange()).with(CONFIRM_ROUTING_KEY);
    }
}
