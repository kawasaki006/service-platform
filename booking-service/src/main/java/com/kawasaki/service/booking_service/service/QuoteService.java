package com.kawasaki.service.booking_service.service;

import com.kawasaki.service.booking_service.dto.CreateQuoteDTO;
import com.kawasaki.service.booking_service.model.Quote;
import com.kawasaki.service.common.events.OrderCancelEvent;
import com.kawasaki.service.common.events.OrderCreateEvent;
import org.springframework.stereotype.Repository;

public interface QuoteService {
    Quote createQuote(CreateQuoteDTO createQuoteDTO, Long providerId);

    void cancelQuote(Long quoteId);

    void handleOrderCreate(OrderCreateEvent orderCreateEvent);

    void handleOrderCancel(OrderCancelEvent orderCancelEvent);
}
