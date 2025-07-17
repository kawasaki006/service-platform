package com.kawasaki.service.booking_service.service;

import com.kawasaki.service.booking_service.dto.CreateQuoteDTO;
import com.kawasaki.service.booking_service.model.Quote;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteService {
    public Quote createQuote(CreateQuoteDTO createQuoteDTO, long providerId);
}
