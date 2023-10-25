package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.CategoryException;
import com.sky.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category req);

    List<Category> findAllCategories();

    Category updateCategory(String categoryId, Category req) throws CategoryException;

    void deleteCategory(String categoryId) throws CategoryException;
}
