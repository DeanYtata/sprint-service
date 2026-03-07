package com.catalog.catalog_service.event;
public class OrderCreatedEvent {
private String eventId;
private Long productId;
private int quantity;
public String getEventId() { return eventId; }
public void setEventId(String eventId) { this.eventId = eventId; }
public Long getProductId() { return productId; }
public void setProductId(Long productId) { this.productId = productId; }
public int getQuantity() { return quantity; }
public void setQuantity(int quantity) { this.quantity = quantity; }
}
