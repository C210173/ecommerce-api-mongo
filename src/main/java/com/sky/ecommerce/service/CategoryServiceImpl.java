package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.CategoryException;
import com.sky.ecommerce.model.Category;
import com.sky.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;


    @Override
    public Category createCategory(Category req) {
        return categoryRepository.save(req);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(String categoryId, Category req) throws CategoryException {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException("Category not found with Id: " + categoryId));
        if (req.getName()!=null){
            existingCategory.setName(req.getName());
        }
        if (req.getDescription()!=null){
            existingCategory.setDescription(req.getDescription());
        }
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(String categoryId) throws CategoryException {
        Optional<Category> categoryToDelete = categoryRepository.findById(categoryId);
        if (categoryToDelete.isPresent()) {
            categoryRepository.delete(categoryToDelete.get());
        } else {
            throw new CategoryException("Category not found");
        }
    }
}
