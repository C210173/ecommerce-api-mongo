package com.sky.ecommerce.response;

import com.sky.ecommerce.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewsResponse {
    private List<Review> reviews;
    private double averageRating;
}
