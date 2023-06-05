package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String SKU;
    @NonNull
    private String Name;
    @NonNull
    private Double Price;
    @NonNull
    private String Category;
    @NonNull
    private String Size;
    @NonNull
    private String Ingredients;
    @NonNull
    private String Launch;

}
