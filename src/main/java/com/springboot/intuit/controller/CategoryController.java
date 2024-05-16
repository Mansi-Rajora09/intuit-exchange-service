package com.springboot.intuit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.intuit.payload.CategoryDto;
import com.springboot.intuit.payload.CategoryDtoResponse;
import com.springboot.intuit.service.CategoryService;
import com.springboot.intuit.utils.AppConstants;
import com.springboot.intuit.utils.Validation;

@RestController
@RequestMapping("/intuit/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;
    private Validation validation;

    public CategoryController(CategoryService categoryService, Validation validation) {
        this.categoryService = categoryService;
        this.validation = validation;
    }

    // Build Add Category REST API
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        validation.validateName(categoryDto.getName());
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(savedCategory);

    }

    // Build Get Category REST API
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    // Build Get All Categories REST API
    @GetMapping
    public ResponseEntity<CategoryDtoResponse> getCategories(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir));
    }

    // Build Update Category REST API
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
            @PathVariable("id") Long categoryId) {

        if (categoryDto.getName() != null) {
            validation.validateName(categoryDto.getName());
        }
        CategoryDto savedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.ok(savedCategory);
    }

    // Build Delete Category REST API

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully!.");
    }
}
