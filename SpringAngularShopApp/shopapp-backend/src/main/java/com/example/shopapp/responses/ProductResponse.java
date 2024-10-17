package com.example.shopapp.responses;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Float price;
    private String thumbnail;
    private String description;

    @JsonProperty("product_images")
    private List<ProductImageResponse> productImages = new ArrayList<>();

    @JsonProperty("category_id")
    private Long categoryId;

    public static ProductResponse fromProduct(Product product) {
        List<ProductImage> productImages = product.getProductImages();
        List<ProductImageResponse> productImageResponses = new ArrayList<>();

        for (ProductImage productImage: productImages) {
            ProductImageResponse productImageResponse = ProductImageResponse.builder()
                    .id(productImage.getId())
                    .imageUrl(productImage.getImageUrl())
                    .build();
            productImageResponses.add(productImageResponse);
        }

        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .categoryId(product.getCategory().getId())
                .productImages(productImageResponses)
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());

        return productResponse;
    }
}