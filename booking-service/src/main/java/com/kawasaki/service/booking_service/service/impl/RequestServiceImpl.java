package com.kawasaki.service.booking_service.service.impl;

import com.kawasaki.service.booking_service.dto.CreateRequestDTO;
import com.kawasaki.service.booking_service.dto.GetRequestOfCategoryDTO;
import com.kawasaki.service.booking_service.dto.RequestAttrSelectionDTO;
import com.kawasaki.service.booking_service.mapper.RequestAttrValueMapper;
import com.kawasaki.service.booking_service.mapper.RequestMapper;
import com.kawasaki.service.booking_service.model.Request;
import com.kawasaki.service.booking_service.model.RequestAttrValue;
import com.kawasaki.service.booking_service.model.RequestExample;
import com.kawasaki.service.booking_service.service.RequestService;
import com.kawasaki.service.common.enume.RequestStatusEnum;
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
    RequestMapper requestMapper;

    @Autowired
    RequestAttrValueMapper requestAttrValueMapper;

    @Override
    @Transactional
    public Request createRequest(CreateRequestDTO createRequestDTO, long userId) {
        Request request = new Request();
        request.setUserId(userId);
        request.setServiceId(createRequestDTO.getServiceId());
        request.setProviderId(createRequestDTO.getProviderId());
        request.setCategoryId(createRequestDTO.getCategoryId());
        request.setNote(createRequestDTO.getNote());

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

        return requestMapper.selectByExample(requestExample);
    }

    @Override
    public List<Request> getSpecificRequestsOfCategory(GetRequestOfCategoryDTO getRequestOfCategoryDTO) {
        RequestExample  requestExample = new RequestExample();
        RequestExample.Criteria criteria = requestExample.createCriteria();

        criteria.andCategoryIdEqualTo(getRequestOfCategoryDTO.getCategoryId());
        criteria.andServiceIdEqualTo(getRequestOfCategoryDTO.getServiceId());

        return requestMapper.selectByExample(requestExample);
    }
}
