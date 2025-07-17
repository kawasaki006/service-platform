package com.kawasaki.service.service_service.controller;

import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.service_service.DTO.CategoryDTO;
import com.kawasaki.service.service_service.model.Category;
import com.kawasaki.service.service_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list-tree")
    public ApiResponse<List<CategoryDTO>> listTree() {
        return ApiResponse.success(categoryService.getAllCategories());
    }
}
