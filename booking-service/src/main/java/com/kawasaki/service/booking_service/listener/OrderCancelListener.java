package com.kawasaki.service.booking_service.listener;

import com.kawasaki.service.booking_service.service.QuoteService;
import com.kawasaki.service.booking_service.service.RequestService;
import com.kawasaki.service.common.constants.RabbitMQConstants;
import com.kawasaki.service.common.events.OrderCancelEvent;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitMQConstants.ORDER_CANCEL_QUEUE)
public class OrderCancelListener {
    @Autowired
    private RequestService requestService;

    @Autowired
    private QuoteService quoteService;

    @RabbitHandler
    public void handleOrderCancel(OrderCancelEvent orderCancelEvent) {
        requestService.handleOrderCancel(orderCancelEvent);
        quoteService.handleOrderCancel(orderCancelEvent);
    }
}
