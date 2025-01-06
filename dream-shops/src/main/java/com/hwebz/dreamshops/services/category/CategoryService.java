package com.hwebz.dreamshops.services.category;

import com.hwebz.dreamshops.exception.AlreadyExistsException;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Category;
import com.hwebz.dreamshops.repositories.CategoryRepository;
import com.hwebz.dreamshops.requests.AddOrUpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(AddOrUpdateCategoryRequest category) {
        return Optional.ofNullable(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(c -> new Category(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException((category != null ? category.getName() : "Category") + " already exists"));
    }

    @Override
    public Category updateCategory(AddOrUpdateCategoryRequest category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(c -> categoryRepository.findByName(c.getName()))
                .map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
            throw new ResourceNotFoundException("Category not found");
        });
    }
}
