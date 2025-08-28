package com.kawasaki.service.booking_service.service.impl;

import com.kawasaki.service.booking_service.dto.CreateQuoteDTO;
import com.kawasaki.service.booking_service.mapper.QuoteMapper;
import com.kawasaki.service.booking_service.mapper.RequestMapper;
import com.kawasaki.service.booking_service.model.Quote;
import com.kawasaki.service.booking_service.model.Request;
import com.kawasaki.service.booking_service.service.QuoteService;
import com.kawasaki.service.common.enume.QuoteStatusEnum;
import com.kawasaki.service.common.enume.RequestStatusEnum;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
