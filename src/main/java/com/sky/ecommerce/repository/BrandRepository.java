package com.sky.ecommerce.repository;

import com.sky.ecommerce.model.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {
    Brand findByName(String brandName);
}
