package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.OrderException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Order;
import com.sky.ecommerce.model.OrderItem;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.OrderRepository;
import com.sky.ecommerce.repository.ProductRepository;
import com.sky.ecommerce.request.OrderItemRequest;
import com.sky.ecommerce.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Override
    public Order createOrder(OrderRequest orderRequest, User user) throws ProductException {

        Order order = new Order();
        order.setUserOrder(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setItems(new ArrayList<>());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setPaymentDetails(orderRequest.getPaymentDetail());
        order.setConsigneeName(orderRequest.getConsigneeName());
        order.setConsigneePhone(orderRequest.getConsigneePhone());

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Optional<Product> productOpt = productRepository.findById(itemRequest.getProductId());
            if (!productOpt.isPresent()) {
                throw new ProductException("Product not found with ID: " + itemRequest.getProductId());
            }
            Product product = productOpt.get();
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());

            order.getItems().add(orderItem);
        }

        double totalPrice = order.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
        int totalItems = order.getItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        order.setTotalPrice(totalPrice);
        order.setTotalItem(totalItems);

        return orderRepository.save(order);
    }


    @Override
    public Order getOrderById(String orderId) throws OrderException {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new OrderException("Order not found with ID: " + orderId);
        }
    }

    @Override
    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserOrder_Id(userId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrderStatus(String orderId, String status) throws OrderException {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            order.setDeliveryDate(LocalDateTime.now());
            return orderRepository.save(order);
        } else {
            throw new OrderException("Order not found with ID: " + orderId);
        }
    }

    @Override
    public void deleteOrder(String orderId) throws OrderException {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            orderRepository.deleteById(orderId);
        } else {
            throw new OrderException("Order not found with ID: " + orderId);
        }
    }
}
