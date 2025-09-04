package com.kawasaki.service.booking_service.service.impl;

import com.kawasaki.service.booking_service.dto.CreateQuoteDTO;
import com.kawasaki.service.booking_service.mapper.QuoteMapper;
import com.kawasaki.service.booking_service.mapper.RequestMapper;
import com.kawasaki.service.booking_service.model.Quote;
import com.kawasaki.service.booking_service.model.QuoteExample;
import com.kawasaki.service.booking_service.model.Request;
import com.kawasaki.service.booking_service.service.QuoteService;
import com.kawasaki.service.common.enume.QuoteStatusEnum;
import com.kawasaki.service.common.enume.RequestStatusEnum;
import com.kawasaki.service.common.events.OrderCancelEvent;
import com.kawasaki.service.common.events.OrderCreateEvent;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class QuoteServiceImpl implements QuoteService {
    @Autowired
    private QuoteMapper quoteMapper;

    @Autowired
    private RequestMapper requestMapper;

    @Override
    public Quote createQuote(CreateQuoteDTO createQuoteDTO, Long providerId) {
        // fetch request and check if it's open
        Request request = requestMapper.selectByPrimaryKey(providerId);
        if (Objects.isNull(request)
                || !Objects.equals(request.getStatus(), RequestStatusEnum.OPEN.getCode())) {
            throw new BizException(BizExceptionCodeEnum.INVALID_REQUEST);
        }

        Quote quote = new Quote();
        quote.setRequestId(createQuoteDTO.getRequestId());
        quote.setUserId(createQuoteDTO.getUserId());
        quote.setProviderId(providerId);
        quote.setPrice(createQuoteDTO.getPrice());
        quote.setNote(createQuoteDTO.getNote());

        quote.setCreatedAt(new Date(System.currentTimeMillis()));
        quote.setUpdatedAt(new Date(System.currentTimeMillis()));
        quote.setStatus(QuoteStatusEnum.PENDING.getCode());

        int rows = quoteMapper.insert(quote);
        if (rows != 1) {
            throw new BizException(BizExceptionCodeEnum.DB_INSERT_FAILED);
        }

        return quote;
    }

    @Override
    public void cancelQuote(Long quoteId) {
        Quote quote = new Quote();
        quote.setRequestId(quoteId);
        quote.setStatus(QuoteStatusEnum.WITHDRAWN.getCode());
        quote.setUpdatedAt(new Date(System.currentTimeMillis()));
        quoteMapper.updateByPrimaryKeySelective(quote);
    }

    @Transactional
    @Override
    public void handleOrderCreate(OrderCreateEvent orderCreateEvent) {
        // accept this quote
        Quote quote = new Quote();
        quote.setId(orderCreateEvent.getQuoteId());
        quote.setStatus(QuoteStatusEnum.ACCEPTED.getCode());
        quote.setUpdatedAt(new Date(System.currentTimeMillis()));
        quoteMapper.updateByPrimaryKeySelective(quote);

        // cancel other quotes
        Quote otherQuote = new Quote();
        otherQuote.setStatus(QuoteStatusEnum.WITHDRAWN.getCode());
        otherQuote.setUpdatedAt(new Date(System.currentTimeMillis()));
        QuoteExample otherQuoteExample = new QuoteExample();
        otherQuoteExample.createCriteria()
                .andRequestIdEqualTo(orderCreateEvent.getRequestId())
                .andIdNotEqualTo(orderCreateEvent.getQuoteId());
        quoteMapper.updateByExampleSelective(otherQuote, otherQuoteExample);
    }

    @Override
    public void handleOrderCancel(OrderCancelEvent orderCancelEvent) {
        // cancel this quote (other quotes already cancelled when order is created)
        Quote quote = new Quote();
        quote.setId(orderCancelEvent.getQuoteId());
        quote.setStatus(QuoteStatusEnum.WITHDRAWN.getCode());
        quote.setUpdatedAt(new Date(System.currentTimeMillis()));
        quoteMapper.updateByPrimaryKeySelective(quote);
    }
}
