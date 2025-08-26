package com.kawasaki.service.booking_service.controller;

import com.kawasaki.service.booking_service.dto.CreateRequestDTO;
import com.kawasaki.service.booking_service.dto.GetRequestOfCategoryDTO;
import com.kawasaki.service.booking_service.dto.RequestDTO;
import com.kawasaki.service.booking_service.model.Request;
import com.kawasaki.service.booking_service.service.RequestService;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.common.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/booking/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    /* users create request
    *  - if service id = null and provider id = null, general request
    *  - if service id and provider id are not null, specific request
    * */
    @PostMapping("/create")
    public ApiResponse<RequestDTO> createRequest(
            @Valid @RequestBody CreateRequestDTO createRequestDTO) {
        //todo: get user id from security context holder
        long userId = 2L;
        Request request = requestService.createRequest(createRequestDTO, userId);

        RequestDTO requestDTO = new RequestDTO();
        BeanUtils.copyProperties(request, requestDTO);
        return ApiResponse.success(requestDTO);
    }

    // provider checks general active requests of a service
    // todo: add location filter
    // todo: add role: admin, provider
    @GetMapping("/general")
    public ApiResponse<List<Request>> getGeneralRequestsOfCategory(
            @Valid @RequestBody GetRequestOfCategoryDTO getRequestOfCategoryDTO) {
        List<Request> list = requestService.getGeneralRequestsOfCategory(getRequestOfCategoryDTO);
        return ApiResponse.success(list);
    }

    // provider checks specific active requests of a service
    // todo: add location filter
    // todo: add role: admin, provider
    @GetMapping("/specific")
    public ApiResponse<List<Request>> getSpecificRequestsOfCategory(
            @Valid @RequestBody GetRequestOfCategoryDTO getRequestOfCategoryDTO) {
        if (Objects.isNull(getRequestOfCategoryDTO.getServiceId())) {
            throw new BizException(BizExceptionCodeEnum.INVALID_REQUEST_PARAMS);
        }

        List<Request> list = requestService.getSpecificRequestsOfCategory(getRequestOfCategoryDTO);
        return ApiResponse.success(list);
    }
}
