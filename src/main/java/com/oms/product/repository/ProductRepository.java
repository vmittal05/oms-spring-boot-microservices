package com.oms.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.oms.product.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    
}
