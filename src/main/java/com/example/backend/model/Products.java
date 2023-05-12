package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("orders")
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    private String id;
    @NonNull
    private String SKU;
    @NonNull
    private String name;
    @NonNull
    private String price;
    @NonNull
    private String category;
    @NonNull
    private String size;
    @NonNull
    private String ingredients;
    @NonNull
    private String launch;

}
