package com.sky.ecommerce.controller;


import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.exception.ReviewException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Review;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.ReviewRequest;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.response.ProductReviewsResponse;
import com.sky.ecommerce.service.ReviewService;
import com.sky.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final UserService userService;
    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req, @RequestHeader("Authorization")String jwt)
            throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Review>> getUserReviews(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Review> reviews = reviewService.getReviewsByUser(user.getId());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable String reviewId, @RequestHeader("Authorization") String jwt)
            throws UserException, ReviewException {
        User user = userService.findUserProfileByJwt(jwt);
        reviewService.deleteReview(reviewId, user);
        ApiResponse res = new ApiResponse();
        res.setMessage("Review deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
