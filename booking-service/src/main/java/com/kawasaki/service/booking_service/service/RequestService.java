package com.kawasaki.service.booking_service.service;

import com.kawasaki.service.booking_service.dto.CreateRequestDTO;
import com.kawasaki.service.booking_service.dto.GetRequestOfCategoryDTO;
import com.kawasaki.service.booking_service.model.Request;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RequestService {
    Request createRequest(CreateRequestDTO createRequestDTO, long userId);

    List<Request> getGeneralRequestsOfCategory(GetRequestOfCategoryDTO getRequestOfCategoryDTO);

    List<Request> getSpecificRequestsOfCategory(GetRequestOfCategoryDTO getRequestOfCategoryDTO);

    void cancelRequest(Long requestId);
}