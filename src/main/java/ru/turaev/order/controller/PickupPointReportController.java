package ru.turaev.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.turaev.order.dto.OrderDto;
import ru.turaev.order.service.OrderService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders/reports/pickup-points")
@RequiredArgsConstructor
public class PickupPointReportController {
    private final OrderService orderService;

    @GetMapping("/{id}/orders")
    public List<OrderDto> getAllOrdersByPeriod(@PathVariable long id,
                                               @RequestParam("begin") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
                                               @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate end) {
        return orderService.getAllNonCancelledOrdersOfPickupPointByPeriod(id, begin, end);
    }
}
