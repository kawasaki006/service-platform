package com.kawasaki.service.service_service.service.impl;

import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.service_service.DTO.CategoryDTO;
import com.kawasaki.service.service_service.mapper.CategoryMapper;
import com.kawasaki.service.service_service.model.Category;
import com.kawasaki.service.service_service.model.CategoryExample;
import com.kawasaki.service.service_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = categoryMapper.selectByExample(new CategoryExample());

        Map<Long, CategoryDTO> idToDTO = new HashMap<>();
        for (Category cat : categoryList) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(cat.getId());
            dto.setName(cat.getName());
            dto.setParentId(cat.getParentId());
            dto.setLevel(cat.getLevel());
            dto.setChildren(new ArrayList<>());
            idToDTO.put(dto.getId(), dto);
        }

        List<CategoryDTO> rootList = new ArrayList<>();
        for (Category cat : categoryList) {
            CategoryDTO dto = idToDTO.get(cat.getId());
            if (dto.getLevel() == 0) {
                // root
                rootList.add(dto);
                continue;
            }
            // non-root
            CategoryDTO parentDTO = idToDTO.get(dto.getParentId());
            if (parentDTO == null) {
                throw new BizException(20000, "Invalid non-root category with NO parent!");
            }
            parentDTO.getChildren().add(dto);
        }

        return rootList;
    }
}
