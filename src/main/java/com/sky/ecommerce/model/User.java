package com.sky.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    private String id;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private Role role;
    private Address address;
    private Payment paymentDetails;
    private LocalDateTime createdAt;
}
