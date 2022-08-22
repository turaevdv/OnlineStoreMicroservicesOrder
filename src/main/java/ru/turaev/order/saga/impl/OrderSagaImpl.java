package ru.turaev.order.saga.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.order.dto.AccountingAndQuantityDto;
import ru.turaev.order.dto.OrderDto;
import ru.turaev.order.exception.IncorrectOrderException;
import ru.turaev.order.exception.IncorrectUserException;
import ru.turaev.order.exception.OrderNotFoundException;
import ru.turaev.order.exception.PickupPointNotFoundException;
import ru.turaev.order.mapper.AccountingAndQuantityMapper;
import ru.turaev.order.model.AccountingAndQuantity;
import ru.turaev.order.model.Order;
import ru.turaev.order.repository.OrderRepository;
import ru.turaev.order.restconsumer.GoodsRestConsumer;
import ru.turaev.order.restconsumer.PickupPointRestConsumer;
import ru.turaev.order.restconsumer.UserRestConsumer;
import ru.turaev.order.saga.OrderSaga;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSagaImpl implements OrderSaga {
    private final OrderRepository orderRepository;
    private final UserRestConsumer userRestConsumer;
    private final PickupPointRestConsumer pickupPointRestConsumer;
    private final GoodsRestConsumer goodsRestConsumer;
    private final AccountingAndQuantityMapper accountingAndQuantityMapper;

    @Transactional
    @Override
    public Order createOrder(OrderDto orderDto) {
        log.info("Trying to create order");
        if (!pickupPointRestConsumer.isPickupPointExist(orderDto.getPickupPointId())) {
            throw new PickupPointNotFoundException("Pickup point with id = " + orderDto.getPickupPointId() + " not found");
        }
        log.info("Pickup point with id = {} exists", orderDto.getPickupPointId());

        if (!userRestConsumer.canUserPlaceOrder(orderDto.getUserId())) {
            throw new IncorrectUserException("User with id = " + orderDto.getUserId() + " can't place order");
        }
        log.info("User with id = {} can place an order", orderDto.getUserId());

        int price = goodsRestConsumer.bookingGoods(orderDto.getAccountingAndQuantities());
        log.info("The order is correct. Final price is {}", price);

        List<AccountingAndQuantity> accountingAndQuantityList = orderDto.getAccountingAndQuantities()
                .stream()
                .map(accountingAndQuantityMapper::fromDto)
                .collect(Collectors.toList());

        Order order = Order.builder()
                .accountingAndQuantities(accountingAndQuantityList)
                .price(price)
                .orderDate(orderDto.getOrderDate())
                .orderTime(orderDto.getOrderTime())
                .pickupPointId(orderDto.getPickupPointId())
                .userId(orderDto.getUserId())
                .isCanceled(false)
                .build();

        orderRepository.save(order);
        log.info("Create an order with id = {}", order.getId());
        return order;
    }

    @Transactional
    @Override
    public Order cancelOrder(long orderId) {
        log.info("Trying to cancel order");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("The order with id = " + orderId + " not found"));

        if (order.isCanceled()) {
            throw new IncorrectOrderException("Order with id = " + orderId + " has already cancelled");
        }

        List<AccountingAndQuantityDto> accountingAndQuantityDtoList = order.getAccountingAndQuantities()
                .stream()
                .map(accountingAndQuantityMapper::toDto)
                .collect(Collectors.toList());

        goodsRestConsumer.returnGoods(accountingAndQuantityDtoList);
        log.info("The goods were removed from the order with id = {} and returned to the storehouse", orderId);
        order.setCanceled(true);
        log.info("The order with id = {} was cancelled", orderId);
        return order;
    }
}
