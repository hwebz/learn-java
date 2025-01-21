package com.hwebz.microservices.product.repositories;

import com.hwebz.microservices.product.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
