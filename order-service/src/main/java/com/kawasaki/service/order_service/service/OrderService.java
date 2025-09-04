package com.kawasaki.service.order_service.service;

import com.kawasaki.service.common.events.OrderCreateEvent;
import com.kawasaki.service.order_service.dto.ConfirmOrderDTO;
import com.kawasaki.service.order_service.dto.SubmitOrderDTO;
import com.kawasaki.service.order_service.model.Order;
import com.kawasaki.service.order_service.vo.OrderConfirmVO;

public interface OrderService {
    OrderConfirmVO confirmOrder(ConfirmOrderDTO confirmOrderDTO, Long userId);

    Order submitOrder(SubmitOrderDTO submitOrderDTO, Long userId);

    void cancelOrder(Long orderId);

    void handleOrderTimeout(OrderCreateEvent orderCreateEvent);
}
