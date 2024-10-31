package org.n8.api.repository;


import org.n8.api.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findById(String orderId);
    List<Order> findByuserId(String userId);
}
