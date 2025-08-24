package com.kawasaki.service.order_service.service;

import com.kawasaki.service.order_service.dto.ConfirmOrderDTO;
import com.kawasaki.service.order_service.dto.SubmitOrderDTO;
import com.kawasaki.service.order_service.model.Order;
import com.kawasaki.service.order_service.vo.OrderConfirmVO;

public interface OrderService {
    OrderConfirmVO confirmOrder(ConfirmOrderDTO confirmOrderDTO);

    Order submitOrder(SubmitOrderDTO submitOrderDTO);
}
