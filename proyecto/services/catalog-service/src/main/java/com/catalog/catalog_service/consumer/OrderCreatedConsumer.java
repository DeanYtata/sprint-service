package com.catalog.catalog_service.consumer;

import com.catalog.catalog_service.config.RabbitMQConfig;
import com.catalog.catalog_service.event.OrderCreatedEvent;
import com.catalog.catalog_service.model.Product;
import com.catalog.catalog_service.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private final ProductRepository productRepository;

    public OrderCreatedConsumer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {

        Product product = productRepository
                .findById(event.getProductId())
                .orElseThrow();

        product.setStock(product.getStock() - event.getQuantity());

        productRepository.save(product);
    }
}