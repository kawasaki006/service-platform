package com.kawasaki.service.booking_service.service;

import com.kawasaki.service.booking_service.dto.CreateQuoteDTO;
import com.kawasaki.service.booking_service.model.Quote;
import org.springframework.stereotype.Repository;

public interface QuoteService {
    Quote createQuote(CreateQuoteDTO createQuoteDTO, long providerId);
}
