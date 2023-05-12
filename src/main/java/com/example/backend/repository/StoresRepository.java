package com.example.backend.repository;

import com.example.backend.model.Stores;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoresRepository extends MongoRepository<Stores,String> {
}
