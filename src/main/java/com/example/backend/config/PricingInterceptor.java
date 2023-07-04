package com.example.backend.config;

import com.example.backend.model.Product;
import com.example.backend.model.TopProduct;
import com.example.backend.repository.ProductRepository;
import com.example.backend.service.AnalysisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PricingInterceptor implements HandlerInterceptor {


    private final AnalysisService analysisService;

    private final ProductRepository productRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<TopProduct> topProducts = analysisService.getTopProducts();
        List<Product> setHigerList = new ArrayList<>();
        List<Product> setLowerList = new ArrayList<>();
        final double MAX_PRICE = 25.00;
        final double MIN_PRICE = 5.00;

        Long getHighestOrder = topProducts.get(0).getAmountOfOrder();
        Long getLowestOrder = topProducts.get(topProducts.size() - 1).getAmountOfOrder();
        Long averageOrder = (getHighestOrder + getLowestOrder) / 2;
        Double highGate = (averageOrder * 1.25);
        Double lowGate = (averageOrder * 0.75);
        System.out.println("highest: " + highGate);
        System.out.println("lowest: " + lowGate);

        // iterate through sortedMap and add Products that need to be changed
        for (TopProduct entry:  topProducts) {
            if (entry.getAmountOfOrder() > highGate) {
                setHigerList.add(productRepository.findProductBySKU(entry.getSku()));
            }

            if (entry.getAmountOfOrder() < lowGate) {
                setLowerList.add(productRepository.findProductBySKU(entry.getSku()));
            }
        }

        for(Product product: setHigerList) {
            if (product.getPrice() < MAX_PRICE) {
                Optional<Product> changedProduct = productRepository.findById(product.getId());
                double oldPrice = changedProduct.get().getPrice();
                changedProduct.get().setPrice(1.1 * oldPrice);
                productRepository.save(changedProduct.get());
            }
        }

        for(Product product: setLowerList) {
            if (product.getPrice() > MIN_PRICE) {
                Optional<Product> changedProduct = productRepository.findById(product.getId());
                double oldPrice = changedProduct.get().getPrice();
                changedProduct.get().setPrice(0.9 * oldPrice);
                productRepository.save(changedProduct.get());
            }
        }
        return true;
    }

}
