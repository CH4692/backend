package com.example.backend.repository;

import com.example.backend.model.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends MongoRepository<OrderItem,String> {
    long countBySKU (String SKU);

    List<OrderItem> getAllBySKU(String SKU);
}
