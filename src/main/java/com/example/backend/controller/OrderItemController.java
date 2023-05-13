package com.example.backend.controller;

import com.example.backend.model.OrderItem;
import com.example.backend.repository.OrderItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderitem")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemsRepository orderItemsRepository;

    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAllOrders() {
        List<OrderItem> orderItems = orderItemsRepository.findAll();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping("/new")
    public ResponseEntity<OrderItem> createOrder() {

        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrder(@PathVariable String id, @RequestBody OrderItem orderItem) {
        return null;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        return null;
    }
}
