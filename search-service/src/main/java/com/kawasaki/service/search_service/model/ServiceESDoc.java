package com.kawasaki.service.search_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(indexName = "service")
public class ServiceESDoc {
    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long serviceId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String categoryName;

    @Field(type = FieldType.Double, index = false)
    private BigDecimal basePrice;

    @Field(type = FieldType.Nested)
    private List<attr> attrs;

    @Data
    public static class attr {
        @Field(type = FieldType.Long)
        private Long optionId;
    }
}
