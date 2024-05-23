package com.example.shopapp.responses;

import com.example.shopapp.models.Category;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListResponse {
    private List<Category> categories;
    private int totalCategories;
}
