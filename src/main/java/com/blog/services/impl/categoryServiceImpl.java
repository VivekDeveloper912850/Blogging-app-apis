package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.payloads.categoryDto;
import com.blog.repository.categoryRepo;
import com.blog.services.serviceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class categoryServiceImpl implements serviceCategory {
    @Autowired
  private categoryRepo cateRepo;
    @Override
    public categoryDto createCategory(categoryDto cateDto) {
        Category category = this.dtoToCategory(cateDto);
        Category categorySaved = this.cateRepo.save(category);
        return this.CategoryToDto(categorySaved);
    }
    @Override
    public categoryDto updateCategory(categoryDto catDto, Integer categoryId) {
        Category category = this.cateRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
        category.setCategoryTitle(catDto.getCategoryTitle());
        category.setCategoryDescription(catDto.getCategoryDescription());

        Category updateUser = this.cateRepo.save(category);
        categoryDto dto =    this.CategoryToDto(updateUser);
        return dto;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.cateRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
        this.cateRepo.delete(category);
    }

    @Override
    public List<categoryDto> getAllCategory() {

        List<Category> category = this.cateRepo.findAll();
        List<categoryDto> categoriesDto =  category.stream().map(categories->this.CategoryToDto(categories)).collect(Collectors.toList());
        return categoriesDto;
    }

    @Override
    public categoryDto getCategoryById(Integer categoryId) {
        Category category = this.cateRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
        return this.CategoryToDto(category);
    }

    private Category dtoToCategory(categoryDto cateDto){
        Category category = new Category();
        category.setCategoryId(category.getCategoryId());
        category.setCategoryTitle(cateDto.getCategoryTitle());
        category.setCategoryDescription(cateDto.getCategoryDescription());
        return category;
    }

    private categoryDto CategoryToDto(Category category){
        categoryDto cateDto = new categoryDto();
        cateDto.setCategoryId(category.getCategoryId());
        cateDto.setCategoryTitle(category.getCategoryTitle());
        cateDto.setCategoryDescription(category.getCategoryDescription());
        return cateDto;
    }

}
