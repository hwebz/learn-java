package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 200, message = "Product name length must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be a positive number")
    @Max(value = 10000000, message = "Price can't be more than 10M")
    private Float price;

    private String thumbnail;
    private String description;

    @NotNull(message = "Category ID is required")
    @JsonProperty("category_id")
    private String categoryId;

    private MultipartFile file;
}
