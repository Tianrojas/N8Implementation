package org.n8.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "boletas")
public class Boleta {
    @Id
    private String id;
    private String eventName;
    private String userId;
    private String price;
    private String purchaseDate;

    // Constructor vacío
    public Boleta() {
    }

    // Constructor con parámetros
    public Boleta(String eventName, String userId, String price, String purchaseDate) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
