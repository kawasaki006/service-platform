package com.kawasaki.service.service_service.mapper;

import com.kawasaki.service.service_service.model.ServiceAttributeValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomServiceAttributeValueMapper {
    int batchInsert(@Param("list") List<ServiceAttributeValue> list);
}
