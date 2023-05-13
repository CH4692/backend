package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
// auto generates getters and setters
@Builder
// constructor made easier
@Document("customers")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String id;
    @NonNull
    private String customerID;
    @NonNull
    private String latitude;
    @NonNull
    private String longitude;
}
