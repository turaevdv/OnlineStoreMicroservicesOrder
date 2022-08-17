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

    private List<AccountingAndQuantityDto> accountingAndQuantityDtos;

    private LocalDate date;

    @DateTimeFormat(pattern = "kk:mm:ss")
    private LocalTime time;
}
