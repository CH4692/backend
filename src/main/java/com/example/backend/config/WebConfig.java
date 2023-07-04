package com.example.backend.config;

import com.example.backend.repository.ProductRepository;
import com.example.backend.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    public final AnalysisService analysisService;
    public final ProductRepository productRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PricingInterceptor(analysisService, productRepository));
    }
}
