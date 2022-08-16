package ru.turaev.order.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс, который нужен для отображения количества единиц товара одного наименования
 */
@Data
@Entity
@Table(name = "goods_and_quantity")
public class GoodsAndQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(nullable = false)
    private int quantity;
}
