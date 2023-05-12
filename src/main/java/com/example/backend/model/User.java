package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
// auto generates getters and setters
@Builder
// constructor made easier
@Document("user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String password;
    @CreatedDate
    private LocalDateTime created;

}