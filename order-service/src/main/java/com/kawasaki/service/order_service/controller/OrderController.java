package com.kawasaki.service.order_service.controller;

import com.kawasaki.service.common.constants.AuthConstants;
import com.kawasaki.service.common.security.UserDetailsImpl;
import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.order_service.dto.ConfirmOrderDTO;
import com.kawasaki.service.order_service.dto.SubmitOrderDTO;
import com.kawasaki.service.order_service.model.Order;
import com.kawasaki.service.order_service.service.OrderService;
import com.kawasaki.service.order_service.vo.OrderConfirmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/confirm")
    public ApiResponse<OrderConfirmVO> confirmOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO,
                                                    @AuthenticationPrincipal Jwt jwt) {
        OrderConfirmVO orderConfirmVO = orderService.confirmOrder(confirmOrderDTO,
                Long.valueOf(jwt.getClaimAsString(AuthConstants.ID)));
        return ApiResponse.success(orderConfirmVO);
    }

    @PostMapping("/submit")
    public ApiResponse<Order> submitOrder(@RequestBody SubmitOrderDTO submitOrderDTO,
                                          @AuthenticationPrincipal Jwt jwt) {
        Order order = orderService.submitOrder(submitOrderDTO,
                Long.valueOf(jwt.getClaimAsString(AuthConstants.ID)));
        return ApiResponse.success(order);
    }

    @PostMapping("/cancel")
    public ApiResponse<?> cancelOrder(@RequestParam Long orderId) {
        orderService.cancelOrder(orderId);
        return ApiResponse.success();
    }
}
