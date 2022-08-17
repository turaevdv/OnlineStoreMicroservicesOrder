package ru.turaev.order.restconsumer;

import ru.turaev.order.dto.AccountingAndQuantityDto;

import java.util.List;

public interface GoodsRestConsumer {
    int bookingGoods(List<AccountingAndQuantityDto> accountingAndQuantityDtos);
    void returnGoods(List<AccountingAndQuantityDto> accountingAndQuantityDtos);
}
