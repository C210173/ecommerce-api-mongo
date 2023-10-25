package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.OrderException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Order;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.OrderRequest;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest orderRequest, User user) throws ProductException, UserException;

    Order getOrderById(String orderId) throws OrderException;

    List<Order> getOrdersByUser(String userId);

    List<Order> getAllOrders();

    Order updateOrderStatus(String orderId, String status) throws OrderException;

    void deleteOrder(String orderId) throws OrderException;
}
