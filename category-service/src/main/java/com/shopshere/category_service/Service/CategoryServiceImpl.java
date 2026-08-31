package com.shopshere.category_service.Service;

import com.shopshere.category_service.DTO.CategoryRequestDto;
import com.shopshere.category_service.DTO.CategoryResponseDto;
import com.shopshere.category_service.Entity.Category;
import com.shopshere.category_service.Exception.CategoryNotFoundException;
import com.shopshere.category_service.Exception.DuplicateCategoryException;
import com.shopshere.category_service.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto request) {

        if (categoryRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateCategoryException("Category with name '" + request.name() + "' already exists");
        }

        Category category = new Category();
        category.setDescription(request.description());
        category.setName(request.name());

        Category savedCategory = categoryRepository.save(category);

        return mapToResponse(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));

        return mapToResponse(category);
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));

        categoryRepository.findByName(request.name())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateCategoryException("Category with name '" + request.name() + "' already exists");
                });

        category.setName(request.name());
        category.setDescription(request.description());

        Category updatedCategory = categoryRepository.save(category);

        return mapToResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
        categoryRepository.deleteById(id);

    }

    public CategoryResponseDto mapToResponse(Category category){
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
