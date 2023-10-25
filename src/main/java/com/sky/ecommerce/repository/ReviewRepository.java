package com.sky.ecommerce.repository;

import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.Review;
import com.sky.ecommerce.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findByUserAndProduct(User user, Product product);

    List<Review> findByProduct_Id(String productId);

    List<Review> findByUser_Id(String userId);
}
