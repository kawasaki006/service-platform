package com.kawasaki.service.booking_service.controller;

import com.kawasaki.service.booking_service.dto.CreateQuoteDTO;
import com.kawasaki.service.booking_service.dto.QuoteDTO;
import com.kawasaki.service.booking_service.model.Quote;
import com.kawasaki.service.booking_service.service.QuoteService;
import com.kawasaki.service.common.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking/quote")
public class QuoteController {
    @Autowired
    QuoteService quoteService;

    @PostMapping("/create")
    public ApiResponse<QuoteDTO> createQuote(
            @Valid @RequestBody CreateQuoteDTO createQuoteDTO,
            @RequestHeader("X_User_Id") String providerIdStr) {
        // get provider id from request header
        long providerId = Long.valueOf(providerIdStr);

        Quote quote = quoteService.createQuote(createQuoteDTO, providerId);
        QuoteDTO quoteDTO = new QuoteDTO();
        BeanUtils.copyProperties(quote, quoteDTO);

        return ApiResponse.success(quoteDTO);
    }
}
