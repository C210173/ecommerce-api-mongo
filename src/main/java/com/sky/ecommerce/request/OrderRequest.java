package com.sky.ecommerce.request;

import com.sky.ecommerce.model.Address;
import com.sky.ecommerce.model.Payment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private List<OrderItemRequest> items;
    private Address shippingAddress;
    private Payment paymentDetail;
    private String consigneeName;
    private String consigneePhone;
}
