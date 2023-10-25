package com.sky.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviews")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Review {
    @Id
    private String id;
    private User user;
    private Product product;
    private int rating;
    private String title;
    private String comment;
    private LocalDateTime createdAt;
}
