package com.hwebz.dreamshops.services.category;

import com.hwebz.dreamshops.models.Category;
import com.hwebz.dreamshops.requests.AddOrUpdateCategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(AddOrUpdateCategoryRequest category);
    Category updateCategory(AddOrUpdateCategoryRequest category, Long id);
    void deleteCategoryById(Long id);
}
