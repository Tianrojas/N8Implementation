package org.n8.api.service;

import org.n8.api.model.Order;
import org.n8.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    // Método para crear o actualizar un pedido
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Método para obtener todos los pedidos
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Método para obtener un pedido por su ID
    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    // Método para eliminar un pedido por su ID
    public void deleteOrderById(String orderId) {
        orderRepository.deleteById(orderId);
    }

    // Método para obtener pedidos por ID de usuario
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByuserId(userId);
    }
}
