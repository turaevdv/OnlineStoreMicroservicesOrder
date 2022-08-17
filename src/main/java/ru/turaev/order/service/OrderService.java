package ru.turaev.order.service;

import ru.turaev.order.dto.OrderDto;
import ru.turaev.order.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDto orderDto);
    Order findOrderById(long id);
    List<Order> getAllNonCancelledOrders();
    Order cancelOrderById(Long id);
}
