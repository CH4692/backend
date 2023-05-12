package com.example.backend.repository;

import com.example.backend.model.Customers;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customers,String> {
}
