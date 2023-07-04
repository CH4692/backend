package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopProduct {
    private String name;
    private String category;
    private String sku;
    private String size;
    private long amountOfOrder;
}
