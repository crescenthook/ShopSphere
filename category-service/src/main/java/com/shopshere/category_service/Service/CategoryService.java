package com.shopshere.category_service.Service;

import com.shopshere.category_service.DTO.CategoryRequestDto;
import com.shopshere.category_service.DTO.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    public CategoryResponseDto createCategory(CategoryRequestDto request);

    public List<CategoryResponseDto> getAllCategories();

    public CategoryResponseDto getCategoryById(Long id);

    public CategoryResponseDto updateCategory (Long id, CategoryRequestDto request);

    public void deleteCategory(Long id);
}
