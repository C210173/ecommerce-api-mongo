package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.CartException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.CartItem;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.CartRepository;
import com.sky.ecommerce.repository.ProductRepository;
import com.sky.ecommerce.request.CartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart addToCart(User user, CartRequest req) throws ProductException {
        Cart userCart = cartRepository.findByUser(user);
        if (userCart == null) {
            userCart = new Cart();
            userCart.setUser(user);
        }

        Optional<Product> productOpt = productRepository.findById(req.getProductId());
        if (!productOpt.isPresent()) {
            throw new ProductException("Product not found with ID: " + req.getProductId());
        }

        Product product = productOpt.get();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(req.getQuantity());

        CartItem existingCartItem = userCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItem.getProduct().getId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
        } else {
            userCart.getItems().add(cartItem);
        }

        double totalPrice = userCart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        int totalItems = userCart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        userCart.setTotalPrice(totalPrice);
        userCart.setTotalItem(totalItems);

        return cartRepository.save(userCart);
    }



    @Override
    public Cart getCartByUser(User user) throws CartException {
        Cart userCart = cartRepository.findByUser(user);

        if (userCart == null) {
            throw new CartException("Cart not found");
        }
        return userCart;
    }

    @Override
    public Cart updateCart(User user, Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }

    @Override
    public Cart updateCartItem(User user, CartRequest req) throws CartException {
        Cart userCart = cartRepository.findByUser(user);

        if (userCart == null) {
            throw new CartException("Cart not found");
        }

        List<CartItem> cartItems = userCart.getItems();

        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(req.getProductId())) {
                item.setQuantity(req.getQuantity());

                double totalPrice = cartItems.stream()
                        .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                        .sum();
                int totalItems = cartItems.stream()
                        .mapToInt(CartItem::getQuantity)
                        .sum();
                userCart.setTotalPrice(totalPrice);
                userCart.setTotalItem(totalItems);

                return cartRepository.save(userCart);
            }
        }

        throw new CartException("Product not found in cart");
    }

    @Override
    public Cart deleteCartItem(User user, String productId) throws CartException {
        Cart userCart = cartRepository.findByUser(user);

        if (userCart == null) {
            throw new CartException("Cart not found");
        }

        List<CartItem> cartItems = userCart.getItems();
        boolean removed = cartItems.removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));

        if (removed) {
            double totalPrice = cartItems.stream()
                    .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                    .sum();
            int totalItems = cartItems.stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();
            userCart.setTotalPrice(totalPrice);
            userCart.setTotalItem(totalItems);

            return cartRepository.save(userCart);
        } else {
            throw new CartException("Product not found in cart");
        }
    }
}
