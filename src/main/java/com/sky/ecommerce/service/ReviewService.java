package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.exception.ReviewException;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.Review;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(ReviewRequest req, User user) throws ProductException;

    List<Review> getReviewsByProduct(String productId);

    double calculateAverageRating(List<Review> reviews);

    List<Review> getReviewsByUser(String id);

    void deleteReview(String reviewId, User user) throws ReviewException;

    List<Review> getAllReviews();

    List<Product> getTopRatedProducts(int limit);

    List<Review> getReviewsForProduct(String productId);
}
