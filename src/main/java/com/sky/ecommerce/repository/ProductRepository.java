package com.sky.ecommerce.repository;

import com.sky.ecommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);
    List<Product> findByNameContaining(String keyword);

    List<Product> findByBrandNameContaining(String keyword);

    List<Product> findByCategoryNameContaining(String keyword);
}
