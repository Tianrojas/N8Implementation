package org.n8.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "boletas")
public class Boleta {
    @Id
    private String id;
    private String eventName;
    private Long userId;
    private double price;
    private LocalDateTime purchaseDate;

    // Constructor vacío
    public Boleta() {
    }

    // Constructor con parámetros
    public Boleta(String eventName, Long userId, double price, LocalDateTime purchaseDate) {
        this.eventName = eventName;
        this.userId = userId;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
