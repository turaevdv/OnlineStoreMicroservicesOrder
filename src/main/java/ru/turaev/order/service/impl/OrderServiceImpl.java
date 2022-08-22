package ru.turaev.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.order.dto.OrderDto;
import ru.turaev.order.exception.OrderNotFoundException;
import ru.turaev.order.mapper.OrderMapper;
import ru.turaev.order.model.Order;
import ru.turaev.order.repository.OrderRepository;
import ru.turaev.order.saga.OrderSaga;
import ru.turaev.order.service.OrderService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderSaga orderSaga;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

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

    @Override
    public List<OrderDto> getAllNonCancelledOrdersOfPickupPointByPeriod(long id, LocalDate begin, LocalDate end) {
        if (begin.isAfter(end)) {
            throw new DateTimeException("The beginning of the period is later than the end");
        }
        return orderRepository.getAllNonCancelledOrdersOfPickupPointByPeriod(id, begin, end)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
