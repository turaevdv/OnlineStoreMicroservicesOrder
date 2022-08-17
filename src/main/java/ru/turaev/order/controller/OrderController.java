package ru.turaev.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.turaev.order.dto.OrderDto;
import ru.turaev.order.model.Order;
import ru.turaev.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping
    public List<Order> getAllNonCancelledOrders() {
        return orderService.getAllNonCancelledOrders();
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @PutMapping("/{id}")
    public Order cancelOrderById(@PathVariable long id) {
        return orderService.cancelOrderById(id);
    }
}
