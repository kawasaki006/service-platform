package com.kawasaki.service.order_service.controller;

import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.order_service.dto.ConfirmOrderDTO;
import com.kawasaki.service.order_service.dto.SubmitOrderDTO;
import com.kawasaki.service.order_service.model.Order;
import com.kawasaki.service.order_service.service.OrderService;
import com.kawasaki.service.order_service.vo.OrderConfirmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/confirm")
    public ApiResponse<OrderConfirmVO> confirmOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO) {
        OrderConfirmVO orderConfirmVO = orderService.confirmOrder(confirmOrderDTO);
        return ApiResponse.success(orderConfirmVO);
    }

    @PostMapping("/submit")
    public ApiResponse<Order> submitOrder(@RequestBody SubmitOrderDTO submitOrderDTO) {
        Order order = orderService.submitOrder(submitOrderDTO);
        return ApiResponse.success(order);
    }
}
