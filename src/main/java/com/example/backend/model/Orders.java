package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
// auto generates getters and setters
@Builder
// constructor made easier
@Document("user")
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    private String id;
    @NonNull
    private String orderID;
    @NonNull
    private String customerID;
    @NonNull
    private String storeID;
    @NonNull
    private String orderDate;
    @NonNull
    private String nItems;
    @NonNull
    private String total;
}
