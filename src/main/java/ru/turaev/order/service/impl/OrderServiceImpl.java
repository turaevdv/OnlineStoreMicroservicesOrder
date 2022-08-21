package ru.turaev.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.order.dto.OrderDto;
import ru.turaev.order.exception.OrderNotFoundException;
import ru.turaev.order.model.Order;
import ru.turaev.order.repository.OrderRepository;
import ru.turaev.order.saga.OrderSaga;
import ru.turaev.order.service.OrderService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderSaga orderSaga;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Order createOrder(OrderDto orderDto) {
        return orderSaga.createOrder(orderDto);
    }

    @Override
    public Order findOrderById(long id) {
        log.info("Trying to find order with id = {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("The order with id = " + id + " not found"));
        log.info("Order with id = {} was found", id);
        return order;
    }

    @Override
    public List<Order> getAllNonCancelledOrders() {
        return orderRepository.getAllByIsCanceledIsFalse();
    }

    @Transactional
    @Override
    public Order cancelOrderById(Long id) {
        return orderSaga.cancelOrder(id);
    }
}
