package com.kawasaki.service.service_service.es.service.impl;

import com.kawasaki.service.service_service.dto.ServiceDTO;
import com.kawasaki.service.service_service.es.model.ServiceESDoc;
import com.kawasaki.service.service_service.es.service.ServiceESService;
import com.kawasaki.service.service_service.feign.ESFeignService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceESServiceImpl implements ServiceESService {
    @Autowired
    ESFeignService esFeignService;

    @Override
    public ServiceESDoc saveService(ServiceDTO service) {
        // convert a service into service es doc
        ServiceESDoc esDoc = new ServiceESDoc();
        esDoc.setServiceId(service.getId());
        esDoc.setTitle(service.getTitle());
        esDoc.setCategoryId(service.getCategoryId());
        esDoc.setBasePrice(service.getBasePrice());

        List<ServiceESDoc.attr> attrList = service.getAttrs().stream()
                .map(attrDTO -> {
                    ServiceESDoc.attr attr = new ServiceESDoc.attr();
                    attr.setOptionId(attrDTO.getOptionId());
                    return attr;
                })
                .collect(Collectors.toList());
        esDoc.setAttrs(attrList);

        // feign call to save
        ServiceESDoc resDoc = esFeignService.saveServiceDoc(esDoc).getData();

        return resDoc;
    }
}
