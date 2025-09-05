package com.kawasaki.service.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawasaki.service.common.constants.PaymentConstants;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.order_service.service.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/stripe")
public class StripeWebhookController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OrderService orderService;

    @PostMapping("/webhook")
    public ApiResponse<?> handleStripeWebhook(@RequestBody String payload,
                                              @RequestHeader("Stripe-Signature") String sigHeader) {
        // todo: get and extract it
        String endpointSecret = "";
        Event event = null;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            throw new BizException(BizExceptionCodeEnum.INVALID_STRIPE_SIGNATURE);
        }

        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (deserializer.getObject().isPresent()) {
            // get payment intent object
            stripeObject = deserializer.getObject().get();
        } else {
            throw new BizException(BizExceptionCodeEnum.STRIPE_DESERIALIZER_ERROR);
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                String orderId = paymentIntent.getMetadata().get(PaymentConstants.STRIPE_ORDER_ID);
                orderService.markPaid(Long.valueOf(orderId), paymentIntent.getId());
                break;
            case "payment_intent.failed":
                // todo: handle failure
                break;
            default:
                throw new BizException(BizExceptionCodeEnum.UNHANDLED_STRIPE_PAYMENT_EVENT);
        }

        return ApiResponse.success(stripeObject);
    }
}
