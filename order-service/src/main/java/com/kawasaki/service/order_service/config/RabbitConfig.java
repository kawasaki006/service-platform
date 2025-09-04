package com.kawasaki.service.order_service.config;

import com.kawasaki.service.common.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public DirectExchange orderEventExchange() {
        return new DirectExchange(RabbitMQConstants.ORDER_EVENT_EXCHANGE, true, false);
    }

    @Bean
    public Queue orderCreateQueue() {
        return QueueBuilder.durable(RabbitMQConstants.ORDER_CREATE_QUEUE).build();
    }

    @Bean
    public Queue orderDelayQueue() {
        return QueueBuilder.durable(RabbitMQConstants.ORDER_DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", RabbitMQConstants.ORDER_EVENT_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", RabbitMQConstants.ORDER_TIMEOUT_KEY)
                .withArgument("x-message-ttl", RabbitMQConstants.ORDER_DELAY_TLL)
                .build();
    }

    @Bean
    public Queue orderTimeoutQueue() {
        return QueueBuilder.durable(RabbitMQConstants.ORDER_TIMEOUT_QUEUE).build();
    }

    @Bean
    public Queue orderCancelQueue() {
        return QueueBuilder.durable(RabbitMQConstants.ORDER_CANCEL_QUEUE).build();
    }

    @Bean
    public Binding orderCreateBinding() {
        return BindingBuilder.bind(orderCreateQueue())
                .to(orderEventExchange())
                .with(RabbitMQConstants.ORDER_CREATE_KEY);
    }

    @Bean
    public Binding orderDelayBinding() {
        return BindingBuilder.bind(orderDelayQueue())
                .to(orderEventExchange())
                .with(RabbitMQConstants.ORDER_CREATE_KEY);
    }

    @Bean
    public Binding orderTimeoutBinding() {
        return BindingBuilder.bind(orderTimeoutQueue())
                .to(orderEventExchange())
                .with(RabbitMQConstants.ORDER_TIMEOUT_KEY);
    }

    @Bean
    public Binding orderCancelBinding() {
        return BindingBuilder.bind(orderCancelQueue())
                .to(orderEventExchange())
                .with(RabbitMQConstants.ORDER_CANCEL_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
