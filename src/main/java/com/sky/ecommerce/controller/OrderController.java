package com.sky.ecommerce.controller;


import com.sky.ecommerce.exception.OrderException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Order;
import com.sky.ecommerce.model.Role;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.OrderRequest;
import com.sky.ecommerce.request.OrderStatusRequest;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.service.OrderService;
import com.sky.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(orderRequest, user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) throws OrderException {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrders(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.getOrdersByUser(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrdersByUserId(@RequestHeader("Authorization") String jwt, @PathVariable String userId) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Order> orders = orderService.getOrdersByUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@RequestHeader("Authorization") String jwt, @PathVariable String orderId, @RequestBody OrderStatusRequest statusRequest)
            throws OrderException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Order order = orderService.updateOrderStatus(orderId, statusRequest.getStatus());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable String orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        orderService.deleteOrder(orderId);
        ApiResponse response = new ApiResponse();
        response.setMessage("Order deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
