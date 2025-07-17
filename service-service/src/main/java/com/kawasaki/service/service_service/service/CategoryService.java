package com.kawasaki.service.service_service.service;

import com.kawasaki.service.service_service.DTO.CategoryDTO;
import com.kawasaki.service.service_service.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryService {
    public List<CategoryDTO> getAllCategories();
}
