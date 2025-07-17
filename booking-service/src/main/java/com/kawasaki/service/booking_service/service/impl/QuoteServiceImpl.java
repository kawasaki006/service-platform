package com.kawasaki.service.booking_service.service.impl;

import com.kawasaki.service.booking_service.dto.CreateQuoteDTO;
import com.kawasaki.service.booking_service.mapper.QuoteMapper;
import com.kawasaki.service.booking_service.model.Quote;
import com.kawasaki.service.booking_service.service.QuoteService;
import com.kawasaki.service.common.enume.QuoteStatusEnum;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuoteServiceImpl implements QuoteService {
    @Autowired
    QuoteMapper quoteMapper;

    @Override
    public Quote createQuote(CreateQuoteDTO createQuoteDTO, long providerId) {
        // TODO: fetch request and check if it's open

        Quote quote = new Quote();
        quote.setRequestId(createQuoteDTO.getRequestId());
        quote.setUserId(createQuoteDTO.getUserId());
        quote.setProviderId(providerId);
        quote.setPrice(createQuoteDTO.getPrice());

        quote.setCreatedAt(new Date(System.currentTimeMillis()));
        quote.setUpdatedAt(new Date(System.currentTimeMillis()));
        quote.setStatus(QuoteStatusEnum.PENDING.getCode());

        int rows = quoteMapper.insert(quote);
        if (rows != 1) {
            throw new BizException(BizExceptionCodeEnum.DB_INSERT_FAILED);
        }

        return quote;
    }
}
