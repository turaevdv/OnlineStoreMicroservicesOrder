package ru.turaev.order.mapper;

import org.mapstruct.Mapper;
import ru.turaev.order.dto.OrderDto;
import ru.turaev.order.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
