package com.kawasaki.service.service_service.es.service;

import com.kawasaki.service.service_service.dto.ServiceDTO;
import com.kawasaki.service.service_service.es.model.ServiceESDoc;

public interface ServiceESService {
    ServiceESDoc saveService(ServiceDTO service);
}
