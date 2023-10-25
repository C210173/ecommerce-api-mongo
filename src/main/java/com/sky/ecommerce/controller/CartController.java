package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.CartException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.CartItem;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.CartRequest;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.service.CartService;
import com.sky.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/add-item")
    public ResponseEntity<Cart> addToCart(@RequestBody CartRequest req, @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.addToCart(user, req);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Cart> getCartHandler(@RequestHeader("Authorization") String jwt) throws CartException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.getCartByUser(user);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/get-cart-items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("Authorization") String jwt) throws UserException, CartException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.getCartByUser(user);
        List<CartItem> cartItems = cart.getItems();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PutMapping("/update-item")
    public ResponseEntity<Cart> updateCartItem(@RequestBody CartRequest req, @RequestHeader("Authorization") String jwt)
            throws UserException, CartException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.updateCartItem(user, req);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/delete-item/{productId}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable String productId, @RequestHeader("Authorization") String jwt)
            throws UserException, CartException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.deleteCartItem(user, productId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<ApiResponse> clearCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        cartService.clearCart(user);
        ApiResponse res = new ApiResponse();
        res.setMessage("Cart cleared successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

