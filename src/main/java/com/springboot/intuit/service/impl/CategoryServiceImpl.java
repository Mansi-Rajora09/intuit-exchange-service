package com.springboot.intuit.service.impl;

import com.google.gson.Gson;
import com.springboot.intuit.entity.Category;
import com.springboot.intuit.exception.ResourceAlreadyException;
import com.springboot.intuit.exception.ResourceNotFoundException;
import com.springboot.intuit.payload.CategoryDto;
import com.springboot.intuit.payload.CategoryDtoResponse;
import com.springboot.intuit.repository.CategoryRepository;
import com.springboot.intuit.service.CategoryService;
import com.springboot.intuit.service.RedisService;
import com.springboot.intuit.utils.AppConstants;
import com.springboot.intuit.utils.Utility;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private Utility utility;
    private RedisService redisService;

    @Value("${spring.redis.default-ttl}")
    private int redisKeyTtl;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Utility utility,
            RedisService redisService) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.utility = utility;
        this.redisService = redisService;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Optional<Category> existingCategoryOptional = categoryRepository.findByName(categoryDto.getName());
        if (existingCategoryOptional.isPresent()) {
            throw new ResourceAlreadyException("Category", "name", categoryDto.getName());
        }

        // Set value in Redis
        String objectString = new Gson().toJson(categoryDto);
        redisService.setValueWithTtl(AppConstants.CATEGORY + categoryDto.getId(), objectString, redisKeyTtl); // Example
                                                                                                              // TTL of
                                                                                                              // 1 hour

        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        // Check if the category exists in Redis
        String value = (String) redisService.getValue(AppConstants.CATEGORY + categoryId);
        if (value != null) {
            // If found in Redis, return it directly
            return new Gson().fromJson(value, CategoryDto.class);
        }

        // If not found in Redis, fetch from the database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        // Map the category to DTO
        CategoryDto response = modelMapper.map(category, CategoryDto.class);

        // Store the fetched value in Redis for future use
        String objectString = new Gson().toJson(response);
        redisService.setValueWithTtl(AppConstants.CATEGORY + categoryId, objectString, redisKeyTtl); // Example TTL of 1
                                                                                                     // hour

        return response;
    }

    @Override
    public CategoryDtoResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Category> categories = categoryRepository.findAll(pageable);

        // get content for page object
        List<Category> listOfCategories = categories.getContent();

        // List<CategoryDto> content = listOfCategories.stream().map(category ->
        // mapToDTO(category)).collect(Collectors.toList());
        List<CategoryDto> content = listOfCategories.stream()
                .map((category) -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        CategoryDtoResponse categoryResponse = new CategoryDtoResponse();
        categoryResponse.setContent(content);
        categoryResponse.setPageNo(categories.getNumber());
        categoryResponse.setPageSize(categories.getSize());
        categoryResponse.setTotalElements(categories.getTotalElements());
        categoryResponse.setTotalPages(categories.getTotalPages());
        categoryResponse.setLast(categories.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        // Update the category
        utility.updateCategory(categoryDto, category);

        // Set updated value in Redis
        String objectString = new Gson().toJson(categoryDto);
        redisService.setValueWithTtl(AppConstants.CATEGORY + categoryId, objectString, redisKeyTtl); // Example TTL of 1
                                                                                                     // hour

        // Save the updated category
        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        // Delete the category from Redis
        redisService.deleteKey(AppConstants.CATEGORY + categoryId);

        categoryRepository.delete(category);
    }

}
