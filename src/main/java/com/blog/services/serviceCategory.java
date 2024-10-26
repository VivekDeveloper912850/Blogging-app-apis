package com.blog.services;

import com.blog.payloads.categoryDto;

import java.util.List;

public interface serviceCategory {
    // Create the Category
    categoryDto createCategory(categoryDto cateDto);

    // Update the Category
    categoryDto updateCategory(categoryDto catDto , Integer categoryId);

    // delete the category
    void  deleteCategory(Integer categoryId );

    // get the category
    // get All Category
    List<categoryDto> getAllCategory();
    // get by Id;
    categoryDto getCategoryById( Integer categoryId);


}
