package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
// auto generates getters and setters
@Builder
// constructor made easier
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
   List<OrderItemDTO> orderList = new ArrayList<>();


    private String totalAmount;
}
