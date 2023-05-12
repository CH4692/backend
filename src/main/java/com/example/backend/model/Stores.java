package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("stores")
@AllArgsConstructor
@NoArgsConstructor
public class Stores {
    @Id
    private String id;
    @NonNull
    private String storeID;
    @NonNull
    private String zipcode;
    @NonNull
    private String state_abbr;
    @NonNull
    private String latitude;
    @NonNull
    private String longitude;
    @NonNull
    private String city;
    @NonNull
    private String state;
    @NonNull
    private String distance;

}
