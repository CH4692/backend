package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
// auto generates getters and setters
@Builder
// constructor made easier
@Document("orderItems")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {
    @Id
    private String id;
    @NonNull
    private String SKU;
    @NonNull
    private String orderID;
}
