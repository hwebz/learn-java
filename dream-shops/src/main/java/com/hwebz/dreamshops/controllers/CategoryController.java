package com.hwebz.dreamshops.controllers;

import com.hwebz.dreamshops.exception.AlreadyExistsException;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Category;
import com.hwebz.dreamshops.requests.AddOrUpdateCategoryRequest;
import com.hwebz.dreamshops.responses.ApiResponse;
import com.hwebz.dreamshops.services.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllCategories(
            @RequestParam(required = false) String name
    ) {
        try {
            ApiResponse response = new ApiResponse(true, "Success", null);
            if (name == null || name.isEmpty()) {
                List<Category> categories = categoryService.getAllCategories();
                response.setData(categories);
            } else {
                Category category = categoryService.getCategoryByName(name);
                response.setData(category);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody AddOrUpdateCategoryRequest category) {
        try {
            Category theCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse(true, "Success", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category theCategory = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse(true, "Success", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody AddOrUpdateCategoryRequest category, @PathVariable Long categoryId) {
        try {
            Category theCategory = categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new ApiResponse(true, "Success", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse(true, "Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }
}
