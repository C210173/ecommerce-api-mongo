package com.sky.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    @Id
    private String id;
    private String name;
    private Brand brand;
    private String description;
    private int quantity;
    private double price;
    private List<String> imageUrl;
    private String operatingSystem;
    private String connectivity;
    private Category category;
    private List<Review> reviews;
}
