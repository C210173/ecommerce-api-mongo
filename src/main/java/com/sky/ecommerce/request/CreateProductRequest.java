package com.sky.ecommerce.request;

import com.sky.ecommerce.model.Brand;
import com.sky.ecommerce.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private String brandName;
    private String description;
    private int quantity;
    private double price;
    private List<String> imageUrl;
    private String operatingSystem;
    private String connectivity;
    private String categoryName;
}
