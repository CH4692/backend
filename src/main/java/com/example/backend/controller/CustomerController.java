package com.example.backend.controller;

import com.example.backend.model.Customer;
import com.example.backend.model.User;
import com.example.backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getCustomerById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping("/new")
    public ResponseEntity<Customer> createCustomer() {

        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        return null;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        return null;
    }
}
