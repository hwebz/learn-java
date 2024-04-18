package com.example.shopapp.controllers;

import com.example.shopapp.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    @GetMapping()
    public ResponseEntity<String> getCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("Get Categories, page = %d, limit = %d", page, limit));
    }

    @PostMapping()
    public ResponseEntity<?> addCategory(
            @Valid @RequestBody CategoryDTO category,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("Add Category");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable String id) {
        return ResponseEntity.ok("Update Category with id = " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id) {
        return ResponseEntity.ok("Delete Category with id = " + id);
    }
}
