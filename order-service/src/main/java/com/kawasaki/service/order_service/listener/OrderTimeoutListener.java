package com.kawasaki.service.order_service.listener;

import com.kawasaki.service.common.constants.RabbitMQConstants;
import com.kawasaki.service.common.events.OrderCreateEvent;
import com.kawasaki.service.order_service.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitMQConstants.ORDER_TIMEOUT_QUEUE)
public class OrderTimeoutListener {
    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void handleOrderTimeout(OrderCreateEvent orderCreateEvent) {
        orderService.handleOrderTimeout(orderCreateEvent);
    }
}
