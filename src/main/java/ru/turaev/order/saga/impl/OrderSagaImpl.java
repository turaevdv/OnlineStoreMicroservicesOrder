package ru.turaev.order.saga.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.order.dto.AccountingAndQuantityDto;
import ru.turaev.order.dto.OrderDto;
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
        if (!pickupPointRestConsumer.isPickupPointExist(orderDto.getPickupPointId())) {
            throw new RuntimeException("Pickup point not found");
        }

        if (!userRestConsumer.canUserPlaceOrder(orderDto.getUserId())) {
            throw new RuntimeException("This user can't place order");
        }

        int price = goodsRestConsumer.bookingGoods(orderDto.getAccountingAndQuantityDtos());

        List<AccountingAndQuantity> accountingAndQuantityList = orderDto.getAccountingAndQuantityDtos()
                .stream()
                .map(accountingAndQuantityMapper::fromDto)
                .collect(Collectors.toList());

        Order order = Order.builder()
                .accountingAndQuantities(accountingAndQuantityList)
                .price(price)
                .orderDate(orderDto.getDate())
                .orderTime(orderDto.getTime())
                .pickupPointId(orderDto.getPickupPointId())
                .userId(orderDto.getUserId())
                .isCanceled(false)
                .build();

        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order cancelOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        List<AccountingAndQuantityDto> accountingAndQuantityDtoList = order.getAccountingAndQuantities()
                .stream()
                .map(accountingAndQuantityMapper::toDto)
                .collect(Collectors.toList());

        goodsRestConsumer.returnGoods(accountingAndQuantityDtoList);
        order.setCanceled(true);
        return order;
    }
}
