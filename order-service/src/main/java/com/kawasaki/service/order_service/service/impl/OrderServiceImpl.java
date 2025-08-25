package com.kawasaki.service.order_service.service.impl;

import com.kawasaki.service.common.enume.OrderStatusEnum;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.order_service.dto.ConfirmOrderDTO;
import com.kawasaki.service.order_service.dto.SubmitOrderDTO;
import com.kawasaki.service.order_service.mapper.OrderMapper;
import com.kawasaki.service.order_service.model.Order;
import com.kawasaki.service.order_service.service.OrderService;
import com.kawasaki.service.order_service.vo.OrderConfirmVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public OrderConfirmVO confirmOrder(ConfirmOrderDTO confirmOrderDTO) {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        BeanUtils.copyProperties(confirmOrderDTO, orderConfirmVO);

        // generate and cache token
        // todo: extract this prefix and token expiry to constants
        String key = "submit-order-token-prefix" + confirmOrderDTO.getUserId() + "-"
                + confirmOrderDTO.getServiceId();
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(key, token, 10, TimeUnit.MINUTES);
        orderConfirmVO.setOrderToken(token);

        return orderConfirmVO;
    }

    @Override
    public Order submitOrder(SubmitOrderDTO submitOrderDTO) {

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
        String key =  "submit-order-token-prefix" + submitOrderDTO.getUserId() + "-" + submitOrderDTO.getServiceId();
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

        order.setUserId(submitOrderDTO.getUserId());
        order.setServiceId(submitOrderDTO.getServiceId());
        order.setBookingId(submitOrderDTO.getQuoteId());
        order.setTotal(submitOrderDTO.getPrice());

        // set status and timestamps
        order.setStatus(OrderStatusEnum.READY.getCode());
        order.setCreatedAt(new Date(System.currentTimeMillis()));
        order.setUpdatedAt(new Date(System.currentTimeMillis()));

        // insert to db
        orderMapper.insert(order);

        // todo: send to mq and log

        return order;
    }
}
