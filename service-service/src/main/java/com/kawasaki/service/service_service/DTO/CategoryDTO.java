package com.kawasaki.service.service_service.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Byte level;
    private List<CategoryDTO> children;
}
