package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TopSeason {
    private int year;
    private Month month;
    private double total;
}
