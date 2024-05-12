package com.springboot.intuit.service;

import com.springboot.intuit.payload.CategoryDto;
import com.springboot.intuit.payload.CategoryDtoResponse;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long categoryId);

    CategoryDtoResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    void deleteCategory(Long categoryId);
}
