package com.example.backend.controller;

import com.example.backend.model.TopGainerStore;
import com.example.backend.model.TopProduct;
import com.example.backend.model.TopSeason;
import com.example.backend.model.TopStore;
import com.example.backend.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    public final AnalysisService analysisService;

    @GetMapping("/all/top-products")
    public ResponseEntity<List<TopProduct>> getAllTopProducts() {
        List<TopProduct> topProducts = analysisService.getTopProducts();
        return ResponseEntity.ok(topProducts);
    }

    @GetMapping("/top-product")
    public ResponseEntity<TopProduct> getTopProduct() {
        TopProduct topProduct = analysisService.getTopProduct();
        return ResponseEntity.ok(topProduct);
    }

    @GetMapping("/all/top-stores")
    public ResponseEntity<List<TopStore>> getAllTopStores() {
        List<TopStore> topStores = analysisService.getTopStores();
        return ResponseEntity.ok(topStores);
    }

    @GetMapping("/top-store")
    public ResponseEntity<TopStore> getTopStore() {
        TopStore topStore = analysisService.getTopStore();
        return ResponseEntity.ok(topStore);
    }

    @GetMapping("/all/top-gainers")
    public ResponseEntity<List<TopGainerStore>> getAllTopGainers() {
        List<TopGainerStore> topGainerStores = analysisService.getTopGainerStores();
        return ResponseEntity.ok(topGainerStores);
    }

    @GetMapping("/top-gainer")
    public ResponseEntity<TopGainerStore> getTopGainer() {
        TopGainerStore topGainerStore = analysisService.getTopGainerStore();
        return ResponseEntity.ok(topGainerStore);
    }

    @GetMapping("/all/top-seasons")
    public ResponseEntity<List<TopSeason>> getAllTopSeasons() {
        List<TopSeason> topSeasons = analysisService.getTopSeasons();;
        return ResponseEntity.ok(topSeasons);
    }

    @GetMapping("/all/total-income")
    public ResponseEntity<Double> getTotalIncome() {
        double totalIncome = analysisService.getTotalIncome();
        return ResponseEntity.ok(totalIncome);
    }




}
