package com.example.backend.controller;

import com.example.backend.model.Product;
import com.example.backend.repository.ProductRepository;
import com.example.backend.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final AnalysisService analysisService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        String objectID = id.replace("\n", "").trim();
        Optional<Product> product = productRepository.findById(objectID);
        if(product.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product.get());
    }

    @PostMapping("/new")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        List<Product> products = productRepository.findAll();
        if( products.size() < 100) {
            product.setSKU("PZ0" + (products.size() + 1));
        } else {
            product.setSKU("PZ" + (products.size() + 1));
        }
        product.setLaunch(LocalDate.now().toString());
        productRepository.save(product);
        return ResponseEntity.ok(product);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return null;
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        String objectID = id.replace("\n","").trim();
        Optional<Product> product = productRepository.findById(objectID);

        if (product.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(product.get());
        return ResponseEntity.noContent().build();
    }
}
