package com.hwebz.dreamshops.services.product;

import com.hwebz.dreamshops.exception.AlreadyExistsException;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Category;
import com.hwebz.dreamshops.models.Image;
import com.hwebz.dreamshops.models.Product;
import com.hwebz.dreamshops.repositories.CategoryRepository;
import com.hwebz.dreamshops.repositories.ImageRepository;
import com.hwebz.dreamshops.repositories.ProductRepository;
import com.hwebz.dreamshops.requests.AddProductRequest;
import com.hwebz.dreamshops.requests.ProductUpdateRequest;
import com.hwebz.dreamshops.responses.ProductImageResponse;
import com.hwebz.dreamshops.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest productRequest) {
        if (productExists(productRequest.getName(), productRequest.getBrand())) {
            throw new AlreadyExistsException(String.format("Product %s %s already exists", productRequest.getBrand(), productRequest.getName()));
        }

        // check if the category is found in the DB
        // If Yes, set is as the new product category
        // If No, the save it as a new category
        Category category = Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory()))
                .orElseGet(() -> {
                    Category newCategory = new Category(productRequest.getCategory());
                    return categoryRepository.save(newCategory);
                });
        return productRepository.save(createProduct(productRequest, category));
    }

    private Product createProduct(AddProductRequest productRequest, Category category) {
        return new Product(
            productRequest.getName(),
            productRequest.getBrand(),
            productRequest.getDescription(),
            productRequest.getPrice(),
            productRequest.getInventory(),
            category
        );
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ResourceNotFoundException("Product not found");
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest productRequest, Long productId) {
        return productRepository.findById(productId)
                .map(exixtingProduct -> updateExistingProduct(exixtingProduct, productRequest))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest productRequest) {
        existingProduct.setName(productRequest.getName());
        existingProduct.setBrand(productRequest.getBrand());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setInventory(productRequest.getInventory());

        Category category = categoryRepository.findByName(productRequest.getCategory());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductsByBrandAndName(brand, name);
    }

    @Override
    public List<ProductResponse> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToResponse).toList();
    }

    @Override
    public ProductResponse convertToResponse(Product product) {
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ProductImageResponse> imagesResponse = images.stream()
                .map(image -> modelMapper.map(image, ProductImageResponse.class))
                .toList();
        productResponse.setImages(imagesResponse);
        return productResponse;
    }
}
