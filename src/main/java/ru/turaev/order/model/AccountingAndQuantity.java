package ru.turaev.order.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс, который нужен для отображения количества единиц товара одного наименования
 */
@Data
@Entity
@Table(name = "accounting_and_quantities")
public class AccountingAndQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "accounting_id", nullable = false)
    private long accountingId;

    @Column(nullable = false)
    private int quantity;
}
