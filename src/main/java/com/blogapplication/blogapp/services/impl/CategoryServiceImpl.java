package com.blogapplication.blogapp.services.impl;

import com.blogapplication.blogapp.entity.Category;
import com.blogapplication.blogapp.exceptions.ResourceNotFoundException;
import com.blogapplication.blogapp.payloads.CategoryDto;
import com.blogapplication.blogapp.repository.CategoryRepo;
import com.blogapplication.blogapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category savedUser = categoryRepo.save(category);
        return this.categoryToDto(savedUser);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = this.categoryRepo.save(category);

        return this.categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                            .orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));

        this.categoryRepo.delete(category);

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> this.categoryToDto(category))
                                    .collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                            .orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        return this.categoryToDto(category);
    }

    private CategoryDto categoryToDto(Category category){
        return this.modelMapper.map(category,CategoryDto.class);
    }

    private Category dtoToCategory(CategoryDto categoryDto){
        return this.modelMapper.map(categoryDto,Category.class);
    }
}
