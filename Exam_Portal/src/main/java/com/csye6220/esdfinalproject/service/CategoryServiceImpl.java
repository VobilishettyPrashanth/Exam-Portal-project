package com.csye6220.esdfinalproject.service;

import com.csye6220.esdfinalproject.dao.CategoryDAO;
import com.csye6220.esdfinalproject.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public void addCategory(Category category) {
        categoryDAO.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDAO.update(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        categoryDAO.delete(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return this.categoryDAO.getCategoryById(categoryId);
    }
}
