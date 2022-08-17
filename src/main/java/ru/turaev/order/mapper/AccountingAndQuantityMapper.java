package ru.turaev.order.mapper;

import org.mapstruct.Mapper;
import ru.turaev.order.dto.AccountingAndQuantityDto;
import ru.turaev.order.model.AccountingAndQuantity;

@Mapper(componentModel = "spring")
public interface AccountingAndQuantityMapper {
    AccountingAndQuantity fromDto(AccountingAndQuantityDto accountingAndQuantityDto);
    AccountingAndQuantityDto toDto(AccountingAndQuantity accountingAndQuantity);
}
