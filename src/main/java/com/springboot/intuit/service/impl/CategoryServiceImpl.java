package com.springboot.intuit.service.impl;

import com.springboot.intuit.entity.Category;
import com.springboot.intuit.exception.ResourceAlreadyException;
import com.springboot.intuit.exception.ResourceNotFoundException;
import com.springboot.intuit.payload.CategoryDto;
import com.springboot.intuit.payload.CategoryDtoResponse;
import com.springboot.intuit.repository.CategoryRepository;
import com.springboot.intuit.service.CategoryService;
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

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private Utility utility;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Utility utility) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.utility = utility;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Optional<Category> existingCategoryOptional = categoryRepository.findByName(categoryDto.getName());
        if (existingCategoryOptional.isPresent()) {
            throw new ResourceAlreadyException("Category", "name", categoryDto.getName());
        }

        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return modelMapper.map(category, CategoryDto.class);
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

        utility.updateCategory(categoryDto, category);

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);
    }

}
