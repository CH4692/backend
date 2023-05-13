package com.example.backend.controller;

import com.example.backend.model.Store;
import com.example.backend.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Store>> getAllOrders() {
        List<Store> stores = storeRepository.findAll();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getOrderById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping("/new")
    public ResponseEntity<Store> createOrder() {

        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> updateOrder(@PathVariable String id, @RequestBody Store store) {
        return null;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        return null;
    }
}
