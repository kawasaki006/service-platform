package com.kawasaki.service.order_service.controller;

import com.kawasaki.service.common.constants.PaymentConstants;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.common.utils.CurrencyUtils;
import com.kawasaki.service.order_service.dto.CreatePaymentDTO;
import com.kawasaki.service.order_service.vo.StripePaymentIntentVO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@RestController
@RequestMapping("/order/payment")
public class PaymentController {

    @PostMapping("/create-payment-intent")
    public ApiResponse<StripePaymentIntentVO> createPaymentIntent(@RequestBody CreatePaymentDTO createPaymentDTO) {
        // todo: extract this key to config
        Stripe.apiKey = "sk_test_51S2il2RCsdJskziUtH4jFcD9k6caGw4pOp87ZmrJAFth5xKbjF0nNs2flfwIOdimEpfuKNUdACAkAWMZGdCIhNNL00JWhUkDT3";

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                // todo: hardcoded currency type
                .setAmount(CurrencyUtils.toStripeAmount(createPaymentDTO.getAmount(), "cad"))
                .setCurrency("cad")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
                .putMetadata(PaymentConstants.STRIPE_ORDER_ID, createPaymentDTO.getOrderId().toString())
                .build();

        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            StripePaymentIntentVO stripePaymentIntentVO = new StripePaymentIntentVO(
                    paymentIntent.getClientSecret()
            );

            return ApiResponse.success(stripePaymentIntentVO);
        } catch (StripeException e) {
            throw new BizException(BizExceptionCodeEnum.CREATE_STRIPE_PAYMENT_EXCEPTION);
        }
    }
}
