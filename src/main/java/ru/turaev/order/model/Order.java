package ru.turaev.order.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Структура, описывающая заказ
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Список товаров, для каждого из которых указано количество единиц */
    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private List<AccountingAndQuantity> accountingAndQuantities;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "order_time", nullable = false)
    @DateTimeFormat(pattern = "kk:mm:ss")
    private LocalTime orderTime;

    /** Id магазина, в котором была совершена покупка */
    @Column(name = "pickup_point_id", nullable = false)
    private long pickupPointId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    /** Общая сумма заказа */
    @Column(nullable = false)
    private int price;

    /** Является ли покупка отменённой (по умолчанию - нет) */
    @Column(name = "is_canceled", nullable = false)
    private boolean isCanceled;
}
