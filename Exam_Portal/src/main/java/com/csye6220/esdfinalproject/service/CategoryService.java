package com.csye6220.esdfinalproject.service;

import com.csye6220.esdfinalproject.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {


    public void addCategory(Category category);
    public void updateCategory(Category category);
    public List<Category> getAllCategories();
    public void deleteCategory(Long categoryId);
    public Category getCategoryById(Long categoryId);

}
