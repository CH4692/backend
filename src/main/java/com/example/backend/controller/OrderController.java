package com.example.backend.controller;

import com.example.backend.model.Order;
import com.example.backend.model.User;
import com.example.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository ordersRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = ordersRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOrderById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping("/new")
    public ResponseEntity<Order> createOrder() {

        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateOrder(@PathVariable String id, @RequestBody Order order) {
        return null;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        return null;
    }
}
