package com.orders.orders_service.service;

import com.orders.orders_service.entity.*;
import com.orders.orders_service.domain.OrderStatus;
import com.orders.orders_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository,
                        RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public OrderEntity createOrder(OrderEntity order) {

        for (OrderItemEntity item : order.getItems()) {

            String url = "http://localhost:8082/products/"
                    + item.getProductId()
                    + "/check-stock?quantity="
                    + item.getQuantity();

            Boolean inStock = restTemplate.getForObject(url, Boolean.class);

            System.out.println("Respuesta stock: " + inStock);

            if (Boolean.FALSE.equals(inStock)) {
                throw new RuntimeException("Producto sin stock");
            }

            if (Boolean.FALSE.equals(inStock)) {
                throw new RuntimeException("Producto sin stock");
            }

            item.setOrder(order);
        }

        order.setStatus(OrderStatus.CREATED);

        return orderRepository.save(order);
    }
}