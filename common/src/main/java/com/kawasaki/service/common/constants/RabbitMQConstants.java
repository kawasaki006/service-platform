package com.kawasaki.service.common.constants;

public class RabbitMQConstants {
    // exchange
    public static final String ORDER_EVENT_EXCHANGE = "order-event-exchange";

    // queues
    public static final String ORDER_CREATE_QUEUE = "order.create.queue";
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";
    public static final String ORDER_TIMEOUT_QUEUE = "order.timeout.queue";
    public static final String ORDER_CANCEL_QUEUE = "order.cancel.queue";

    // routing keys
    public static final String ORDER_CREATE_KEY =  "order.create.order";
    public static final String ORDER_TIMEOUT_KEY = "order.timeout";
    public static final String ORDER_CANCEL_KEY = "order.cancel.order";

    // ttl
    public static final int ORDER_DELAY_TLL = 30 * 60 * 1000; // 30mins
}
