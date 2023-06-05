package com.example.backend.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopGainerStore {
    private String city;
    private String state;
    private double totalIncome;
}
