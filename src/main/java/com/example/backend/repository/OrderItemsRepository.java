package com.example.backend.repository;

import com.example.backend.model.OrderItems;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends MongoRepository<OrderItems,String> {
}
