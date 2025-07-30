package com.kawasaki.service.service_service.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ServiceESDoc {
    private Long serviceId;
    private String title;
    private Long categoryId;
    private BigDecimal basePrice;
    private List<attr> addrs;

    @Data
    public static class attr {
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}
