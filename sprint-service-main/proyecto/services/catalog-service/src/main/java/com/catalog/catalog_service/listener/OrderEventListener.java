package com.catalog.catalog_service.listener;import com.catalog.catalog_service.config.RabbitMQConfig;
import com.catalog.catalog_service.event.OrderCreatedEvent;
import com.catalog.catalog_service.model.ProcessedEvent;
import com.catalog.catalog_service.repository.ProcessedEventRepository;
import com.catalog.catalog_service.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;@Component
public class OrderEventListener {
@Autowired
private ProductRepository productRepository;

@Autowired
private ProcessedEventRepository processedEventRepository;

@RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
public void handleOrderCreated(OrderCreatedEvent event) {
    if (processedEventRepository.existsById(event.getEventId())) {
        System.out.println("Evento ya procesado: " + event.getEventId());
        return;
    }
    productRepository.findById(event.getProductId()).ifPresent(product -> {
        int newStock = product.getStock() - event.getQuantity();
        if (newStock < 0) {
            System.out.println("Stock insuficiente para producto: " + event.getProductId());
            return;
        }
        product.setStock(newStock);
        productRepository.save(product);
        processedEventRepository.save(new ProcessedEvent(event.getEventId()));
        System.out.println("Stock descontado para producto " + event.getProductId() + ". Stock restante: " + newStock);
    });
}
}
