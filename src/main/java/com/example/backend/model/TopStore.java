package com.example.backend.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopStore {
    private String city;
    private String state;
    private long amountOfOrder;
}
