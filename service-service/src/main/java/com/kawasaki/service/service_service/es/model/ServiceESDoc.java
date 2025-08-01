package com.kawasaki.service.service_service.es.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ServiceESDoc {
    private String id;
    private Long serviceId;
    private String title;
    private Long categoryId;
    private BigDecimal basePrice;
    private List<attr> attrs;

    @Data
    public static class attr {
        private Long optionId;
    }
}
