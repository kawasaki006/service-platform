package com.kawasaki.service.order_service.service.impl;

import com.kawasaki.service.common.constants.RabbitMQConstants;
import com.kawasaki.service.common.enume.OrderStatusEnum;
import com.kawasaki.service.common.events.OrderCancelEvent;
import com.kawasaki.service.common.events.OrderCreateEvent;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.order_service.dto.ConfirmOrderDTO;
import com.kawasaki.service.order_service.dto.SubmitOrderDTO;
import com.kawasaki.service.order_service.mapper.OrderMapper;
import com.kawasaki.service.order_service.model.Order;
import com.kawasaki.service.order_service.service.OrderService;
import com.kawasaki.service.order_service.vo.OrderConfirmVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public OrderConfirmVO confirmOrder(ConfirmOrderDTO confirmOrderDTO, Long userId) {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        BeanUtils.copyProperties(confirmOrderDTO, orderConfirmVO);

        // generate and cache token
        // todo: extract this prefix and token expiry to constants
        String key = "submit-order-token-prefix" + userId + "-"
                + confirmOrderDTO.getServiceId();
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(key, token, 10, TimeUnit.MINUTES);
        orderConfirmVO.setOrderToken(token);

        return orderConfirmVO;
    }

    @Override
    public Order submitOrder(SubmitOrderDTO submitOrderDTO, Long userId) {

        // verify token
        if (submitOrderDTO.getOrderToken() == null) {
            throw new BizException(BizExceptionCodeEnum.INVALID_OR_EMPTY_ORDER_TOKEN);
        }

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "return redis.call('del', KEYS[1]) " +
                        "else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        // todo: extract this prefix to constants
        String key =  "submit-order-token-prefix" + userId + "-" + submitOrderDTO.getServiceId();
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), submitOrderDTO.getOrderToken());

        if (result == 0) {
            throw new BizException(BizExceptionCodeEnum.INVALID_OR_EMPTY_ORDER_TOKEN);
        }

        // valid token, continue creating order
        Order order = new Order();

        // check validity of service

        // check validity of quote

        // todo: random order number string
        String orderSn = "12345";
        order.setOrderNumber(orderSn);

        order.setUserId(userId);
        order.setServiceId(submitOrderDTO.getServiceId());
        order.setRequestId(submitOrderDTO.getRequestId());
        order.setQuoteId(submitOrderDTO.getQuoteId());
        order.setTotal(submitOrderDTO.getPrice());

        // set status and timestamps
        order.setStatus(OrderStatusEnum.UNPAID.getCode());
        order.setCreatedAt(new Date(System.currentTimeMillis()));
        order.setUpdatedAt(new Date(System.currentTimeMillis()));

        // insert to db
        int rows = orderMapper.insert(order);
        if (rows != 1) {
            throw new BizException(BizExceptionCodeEnum.ORDER_CREATE_DB_INSERT_EXCEPTION);
        }

        // send to mq
        OrderCreateEvent orderCreateEvent = new OrderCreateEvent();
        orderCreateEvent.setOrderId(order.getId());
        orderCreateEvent.setRequestId(order.getRequestId());
        orderCreateEvent.setQuoteId(order.getQuoteId());
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.ORDER_EVENT_EXCHANGE,
                RabbitMQConstants.ORDER_CREATE_KEY,
                orderCreateEvent);

        return order;
    }

    @Override
    public void cancelOrder(Long orderId) {
        // get original order
        Order originalOrder = orderMapper.selectByPrimaryKey(orderId);
        if (Objects.isNull(originalOrder) || Objects.equals(originalOrder.getStatus(), OrderStatusEnum.CANCELLED.getCode())) {
            return;
        }

        // update status to cancelled
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatusEnum.CANCELLED.getCode());
        order.setUpdatedAt(new Date(System.currentTimeMillis()));
        int rows = orderMapper.updateByPrimaryKeySelective(order);
        if (rows != 1) {
            throw new BizException(BizExceptionCodeEnum.ORDER_CANCEL_EXCEPTION);
        }

        // send to mq
        OrderCancelEvent orderCancelEvent = new OrderCancelEvent();
        orderCancelEvent.setOrderId(originalOrder.getId());
        orderCancelEvent.setRequestId(originalOrder.getRequestId());
        orderCancelEvent.setQuoteId(originalOrder.getQuoteId());
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.ORDER_EVENT_EXCHANGE,
                RabbitMQConstants.ORDER_CANCEL_KEY,
                orderCancelEvent);
    }

    @Override
    public void handleOrderTimeout(OrderCreateEvent orderCreateEvent) {
        Order order = orderMapper.selectByPrimaryKey(orderCreateEvent.getOrderId());
        if (Objects.isNull(order)) {
            return;
        }
        if (order.getStatus().equals(OrderStatusEnum.UNPAID.getCode())) {
            cancelOrder(orderCreateEvent.getOrderId());
        }
    }
}
