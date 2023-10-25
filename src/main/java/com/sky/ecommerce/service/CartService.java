package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.CartException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.CartItem;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.CartRequest;

public interface CartService {
    Cart addToCart(User user, CartRequest req) throws ProductException;

    Cart getCartByUser(User user) throws CartException;

    Cart updateCart(User user, Cart cart);

    void clearCart(User user);

    Cart updateCartItem(User user, CartRequest req) throws CartException;

    Cart deleteCartItem(User user, String productId) throws CartException;
}
