package com.kawasaki.service.booking_service.service.impl;

import com.kawasaki.service.booking_service.dto.CreateRequestDTO;
import com.kawasaki.service.booking_service.dto.GetRequestOfCategoryDTO;
import com.kawasaki.service.booking_service.dto.RequestAttrSelectionDTO;
import com.kawasaki.service.booking_service.mapper.QuoteMapper;
import com.kawasaki.service.booking_service.mapper.RequestAttrValueMapper;
import com.kawasaki.service.booking_service.mapper.RequestMapper;
import com.kawasaki.service.booking_service.model.*;
import com.kawasaki.service.booking_service.service.RequestService;
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
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private RequestAttrValueMapper requestAttrValueMapper;

    @Autowired
    private QuoteMapper quoteMapper;

    @Override
    @Transactional
    public Request createRequest(CreateRequestDTO createRequestDTO, long userId) {
        Request request = new Request();
        request.setUserId(userId);
        request.setServiceId(createRequestDTO.getServiceId());
        request.setProviderId(createRequestDTO.getProviderId());
        request.setCategoryId(createRequestDTO.getCategoryId());
        request.setNote(createRequestDTO.getNote());
        // todo: check if time preference enum is valid
        request.setTimePreference(createRequestDTO.getTimePreference());
        request.setBudget(createRequestDTO.getBudget());

        request.setCreatedAt(new Date(System.currentTimeMillis()));
        request.setUpdatedAt(new Date(System.currentTimeMillis()));
        request.setStatus(RequestStatusEnum.OPEN.getCode());

        int rows = requestMapper.insert(request);
        if (rows != 1) {
            throw new BizException(BizExceptionCodeEnum.DB_INSERT_FAILED);
        }

        for (RequestAttrSelectionDTO attrDTO : createRequestDTO.getAttrSelections()) {
            RequestAttrValue requestAttrValue = new RequestAttrValue();
            requestAttrValue.setRequestId(request.getId());
            requestAttrValue.setAttrId(attrDTO.getAttrId());
            requestAttrValue.setOptionId(attrDTO.getOptionId());
            requestAttrValue.setIsDeleted(false);

            rows = requestAttrValueMapper.insert(requestAttrValue);
            if (rows != 1) {
                throw new BizException(BizExceptionCodeEnum.DB_INSERT_FAILED);
            }
        }

        return request;
    }

    @Override
    public List<Request> getGeneralRequestsOfCategory(GetRequestOfCategoryDTO getRequestOfCategoryDTO) {
        RequestExample  requestExample = new RequestExample();
        RequestExample.Criteria criteria = requestExample.createCriteria();

        criteria.andCategoryIdEqualTo(getRequestOfCategoryDTO.getCategoryId());
        criteria.andServiceIdIsNull();
        criteria.andStatusEqualTo(RequestStatusEnum.OPEN.getCode());

        return requestMapper.selectByExample(requestExample);
    }

    @Override
    public List<Request> getSpecificRequestsOfCategory(GetRequestOfCategoryDTO getRequestOfCategoryDTO) {
        RequestExample requestExample = new RequestExample();
        RequestExample.Criteria criteria = requestExample.createCriteria();

        criteria.andCategoryIdEqualTo(getRequestOfCategoryDTO.getCategoryId());
        criteria.andServiceIdEqualTo(getRequestOfCategoryDTO.getServiceId());
        criteria.andStatusEqualTo(RequestStatusEnum.OPEN.getCode());

        return requestMapper.selectByExample(requestExample);
    }

    @Transactional
    @Override
    public void cancelRequest(Long requestId) {
        // mark request as cancelled
        Request request = new Request();
        request.setId(requestId);
        request.setStatus(RequestStatusEnum.CANCELLED.getCode());
        request.setUpdatedAt(new Date(System.currentTimeMillis()));
        requestMapper.updateByPrimaryKeySelective(request);

        // mark attrs as deleted
        deleteAttrValuesByRequestId(requestId);

        // cancel related quotes
        Quote quote = new Quote();
        quote.setStatus(QuoteStatusEnum.WITHDRAWN.getCode());
        quote.setUpdatedAt(new Date(System.currentTimeMillis()));
        QuoteExample quoteExample = new QuoteExample();
        quoteExample.createCriteria().andRequestIdEqualTo(requestId);
        quoteMapper.updateByExampleSelective(quote, quoteExample);
    }

    @Transactional
    @Override
    public void handleOrderCreate(OrderCreateEvent orderCreateEvent) {
        // close request
        Request request = new Request();
        request.setId(orderCreateEvent.getRequestId());
        request.setStatus(RequestStatusEnum.CLOSED.getCode());
        request.setUpdatedAt(new Date(System.currentTimeMillis()));
        requestMapper.updateByPrimaryKeySelective(request);

        // mark attrs as deleted
        deleteAttrValuesByRequestId(orderCreateEvent.getRequestId());
    }

    @Transactional
    @Override
    public void handleOrderCancel(OrderCancelEvent orderCancelEvent) {
        // close request
        Request request = new Request();
        request.setId(orderCancelEvent.getRequestId());
        request.setStatus(RequestStatusEnum.CANCELLED.getCode());
        request.setUpdatedAt(new Date(System.currentTimeMillis()));
        requestMapper.updateByPrimaryKeySelective(request);

        // mark attrs as deleted
        deleteAttrValuesByRequestId(orderCancelEvent.getRequestId());
    }

    private void deleteAttrValuesByRequestId(Long requestId) {
        RequestAttrValue requestAttrValue = new RequestAttrValue();
        requestAttrValue.setIsDeleted(true);
        RequestAttrValueExample requestAttrValueExample = new RequestAttrValueExample();
        requestAttrValueExample.createCriteria().andRequestIdEqualTo(requestId);
        requestAttrValueMapper.updateByExampleSelective(requestAttrValue, requestAttrValueExample);
    }
}
