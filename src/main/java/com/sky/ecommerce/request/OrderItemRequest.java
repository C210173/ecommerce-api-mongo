package com.sky.ecommerce.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemRequest {
    private String productId;
    private int quantity;
}
