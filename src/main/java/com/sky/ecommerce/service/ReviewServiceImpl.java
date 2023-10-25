package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.exception.ReviewException;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.Review;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.ProductRepository;
import com.sky.ecommerce.repository.ReviewRepository;
import com.sky.ecommerce.request.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ProductException("Product not found with ID: " + req.getProductId()));

        Optional<Review> existingReview = reviewRepository.findByUserAndProduct(user, product);

        if (existingReview.isPresent()) {
            Review reviewToUpdate = existingReview.get();
            reviewToUpdate.setRating(req.getRating());
            reviewToUpdate.setTitle(req.getTitle());
            reviewToUpdate.setComment(req.getComment());
            reviewToUpdate.setCreatedAt(LocalDateTime.now());
            return reviewRepository.save(reviewToUpdate);
        } else {
            Review newReview = new Review();
            newReview.setUser(user);
            newReview.setProduct(product);
            newReview.setRating(req.getRating());
            newReview.setTitle(req.getTitle());
            newReview.setComment(req.getComment());
            newReview.setCreatedAt(LocalDateTime.now());
            return reviewRepository.save(newReview);
        }
    }

    @Override
    public List<Review> getReviewsByProduct(String productId) {
        return reviewRepository.findByProduct_Id(productId);
    }

    @Override
    public double calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        int totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }

        return (double) totalRating / reviews.size();
    }

    @Override
    public List<Review> getReviewsByUser(String userId) {
        return reviewRepository.findByUser_Id(userId);
    }
    @Override
    public void deleteReview(String reviewId, User user) throws ReviewException {
        Optional<Review> reviewToDelete = reviewRepository.findById(reviewId);
        if (reviewToDelete.isPresent()) {
            Review review = reviewToDelete.get();
            if (review.getUser().getId().equals(user.getId())) {
                reviewRepository.deleteById(reviewId);
            } else {
                throw new ReviewException("You are not authorized to delete this review.");
            }
        } else {
            throw new ReviewException("Review not found with ID: " + reviewId);
        }
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Product> getTopRatedProducts(int limit) {
        List<Product> products = productRepository.findAll();

        products.sort((product1, product2) -> Double.compare(
                calculateAverageRating(getReviewsForProduct(product2.getId())),
                calculateAverageRating(getReviewsForProduct(product1.getId()))
        ));

        return products.subList(0, Math.min(limit, products.size()));
    }

    @Override
    public List<Review> getReviewsForProduct(String productId) {
        return reviewRepository.findByProduct_Id(productId);
    }
}
