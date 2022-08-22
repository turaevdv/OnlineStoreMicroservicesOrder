package ru.turaev.order.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class OrderDto {
    private long userId;
    private long pickupPointId;
    private LocalDate orderDate;
    @DateTimeFormat(pattern = "kk:mm:ss")
    private LocalTime orderTime;
    private List<AccountingAndQuantityDto> accountingAndQuantities;
    private int price;
}
