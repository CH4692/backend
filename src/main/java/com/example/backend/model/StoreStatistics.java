package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
// auto generates getters and setters
@Builder
// constructor made easier
@Document("customers")
@AllArgsConstructor
@NoArgsConstructor
public class StoreStatistics {
    private int count;
    private double total;

}
