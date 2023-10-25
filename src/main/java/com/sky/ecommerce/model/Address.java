package com.sky.ecommerce.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String postalCode;
    private String street;
    private String city;
    private String state;
    private String country;
}
