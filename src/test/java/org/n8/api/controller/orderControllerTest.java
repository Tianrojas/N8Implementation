package org.n8.api.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.n8.api.model.Order;
import org.n8.api.repository.OrderRepository;
import org.n8.api.service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class orderControllerTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder() {
        Order order = new Order("user123", "Coca-Cola", "2", "5.00", "Pending", "2023-10-30T19:00:00");
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.saveOrder(order);

        assertEquals(order, savedOrder);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order("user123", "Coca-Cola", "2", "5.00", "Pending", "2023-10-30T19:00:00");
        Order order2 = new Order("user456", "Pepsi", "1", "3.00", "Completed", "2023-10-29T18:00:00");
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        assertEquals(orders, result);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderByIdFound() {
        String orderId = "1";
        Order order = new Order("user123", "Coca-Cola", "2", "5.00", "Pending", "2023-10-30T19:00:00");
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(orderId);

        assertEquals(order, result.get());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testGetOrderByIdNotFound() {
        String orderId = "999";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(orderId);

        assertEquals(Optional.empty(), result);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testDeleteOrderById() {
        String orderId = "1";
        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteOrderById(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void testGetOrdersByUserId() {
        String userId = "user123";
        Order order1 = new Order(userId, "Coca-Cola", "2", "5.00", "Pending", "2023-10-30T19:00:00");
        Order order2 = new Order(userId, "Pepsi", "1", "3.00", "Completed", "2023-10-29T18:00:00");
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findByUserId(userId)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByUserId(userId);

        assertEquals(2, result.size());
        assertEquals(orders, result);
        verify(orderRepository, times(1)).findByUserId(userId);
    }
}
