package com.csye6220.esdfinalproject.dao;


import com.csye6220.esdfinalproject.model.Category;
import java.util.List;


public interface CategoryDAO {


    public void save(Category category);

    public void update(Category category);

    public void delete(Category category);

   public List<Category> getAllCategories();


    public Category getCategoryById(Long categoryId);
}
