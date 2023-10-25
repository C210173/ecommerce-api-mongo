package com.sky.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "carts")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cart {
    @Id
    private String id;
    private User user;
    private List<CartItem> items = new ArrayList<>();
    private double totalPrice;
    private int totalItem;
}
