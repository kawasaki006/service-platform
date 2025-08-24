package com.kawasaki.service.booking_service.controller;

import com.kawasaki.service.booking_service.dto.CreateRequestDTO;
import com.kawasaki.service.booking_service.dto.RequestDTO;
import com.kawasaki.service.booking_service.model.Request;
import com.kawasaki.service.booking_service.service.RequestService;
import com.kawasaki.service.common.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    @PostMapping("/create")
    public ApiResponse<RequestDTO> createRequest(
            @Valid @RequestBody CreateRequestDTO createRequestDTO) {
        //todo: get user id from security context holder
        long userId = Long.valueOf(2);
        Request request = requestService.createRequest(createRequestDTO, userId);

        RequestDTO requestDTO = new RequestDTO();
        BeanUtils.copyProperties(request, requestDTO);
        return ApiResponse.success(requestDTO);
    }
}
