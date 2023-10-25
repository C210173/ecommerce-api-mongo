package com.sky.ecommerce.repository;

import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUser(User user);

    void deleteByUser(User user);
}
