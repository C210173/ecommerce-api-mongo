package com.sky.ecommerce.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewRequest {
    private String productId;
    private int rating;
    private String title;
    private String comment;
}
