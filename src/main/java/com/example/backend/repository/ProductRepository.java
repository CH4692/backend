package com.example.backend.repository;

import com.example.backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Product findProductBySKU(String sku);


    void deleteBySKU(String SKU);
}
