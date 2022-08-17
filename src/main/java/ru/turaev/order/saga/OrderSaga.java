package ru.turaev.order.saga;

import ru.turaev.order.dto.OrderDto;
import ru.turaev.order.model.Order;

public interface OrderSaga {
    Order createOrder(OrderDto orderDto);
    Order cancelOrder(long orderId);
}
