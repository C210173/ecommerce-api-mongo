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
@Document(collection = "orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    @Id
    private String id;
    private User userOrder;
    private String consigneeName;
    private String consigneePhone;
    private List<OrderItem> items;
    private double totalPrice;
    private int totalItem;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String status;
    private Address shippingAddress;
    private Payment paymentDetails;
}
