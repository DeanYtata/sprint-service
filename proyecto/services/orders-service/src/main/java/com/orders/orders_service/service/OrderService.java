package com.orders.orders_service.service;
import com.orders.orders_service.config.RabbitMQConfig;
import com.orders.orders_service.domain.OrderStatus;
import com.orders.orders_service.entity.OrderEntity;
import com.orders.orders_service.entity.OrderItemEntity;
import com.orders.orders_service.event.OrderCreatedEvent;
import com.orders.orders_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;
@Service
public class OrderService {
private final OrderRepository orderRepository;
private final RestTemplate restTemplate;
private final RabbitTemplate rabbitTemplate;

public OrderService(OrderRepository orderRepository,
                    RestTemplate restTemplate,
                    RabbitTemplate rabbitTemplate) {
    this.orderRepository = orderRepository;
    this.restTemplate = restTemplate;
    this.rabbitTemplate = rabbitTemplate;
}

public OrderEntity createOrder(OrderEntity order) {
    for (OrderItemEntity item : order.getItems()) {
        String url = "http://localhost:8082/products/"
                + item.getProductId()
                + "/check-stock?quantity="
                + item.getQuantity();
        try {
            restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 409) {
                throw new RuntimeException("Producto sin stock: " + item.getProductId());
            }
            throw e;
        }
        item.setOrder(order);
    }

    order.setStatus(OrderStatus.CREATED);
    OrderEntity saved = orderRepository.save(order);

    for (OrderItemEntity item : saved.getItems()) {
        OrderCreatedEvent event = new OrderCreatedEvent(
            UUID.randomUUID().toString(),
            item.getProductId(),
            item.getQuantity()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_CREATED_QUEUE, event);
        System.out.println("Evento publicado para producto: " + item.getProductId());
    }

    return saved;
}
}
